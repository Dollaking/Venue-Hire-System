/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Venue Hire System for COMP2511.
 *
 * A basic prototype to serve as the "back-end" of a venue hire system. Input
 * and output is in JSON format.
 *
 * @author Robert Clifton-Everest
 *
 */
public class VenueHireSystem {

    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
	private List <Venue> venueList = new ArrayList<Venue>(); 
	
	
    public VenueHireSystem() {

    }
    /**
     * This the function that handles with parsing the input json and executing the command
     * @param json This is the JSON input
     */

    private void processCommand(JSONObject json) {
    	
        switch (json.getString("command")) {
        case "room":
            initRoom(json);   
            break;

        case "request":
            System.out.println(request(json).toString(2));
            break;
        
        case "change":
        	System.out.println(change(json).toString(2));
        	
        	break;
        	
        case "cancel":
        	cancel(json);       	
        	break;
            
        case "list":
        	System.out.println(list(json).toString(2));
        	break;

        }
    }
    
	/**
	 * This is the room command, which would initialise and add the room into the system.
	 * @param json This is the input json file.
	 */

	private void initRoom(JSONObject json) {
		String venue = json.getString("venue");
		String room = json.getString("room");
		String size = json.getString("size");
		Venue selectedVenue = getVenueByName(venue);        
		selectedVenue.addRoom(room, size);
	}
	
    
    /**
     * This is the request command execution
     * @param json This is the input json file.
     * @return The JSON result of the request
     */

    public JSONObject request(JSONObject json) {
    	
        String id = json.getString("id");
        LocalDate start = LocalDate.parse(json.getString("start"));
        LocalDate end = LocalDate.parse(json.getString("end"));
        int small = json.getInt("small");
        int medium = json.getInt("medium");
        int large = json.getInt("large");
        JSONObject result = new JSONObject();

        // TODO Process the request commmand
        for (Venue venue : venueList) {
        	List<String> roomsBooked = venue.makeReservation(id, start, end, small, medium, large);
        	if (!roomsBooked.equals(new ArrayList <String>())) {
        		result.put("status", "success");
        		result.put("venue", venue.getVenueName());
        		result.put("rooms", roomsBooked);
        		return result;
        	}
        }
        
        result.put("status", "rejected");
        return result;
    }

	/**
	 * This is the execution of the change command
	 * @param json This is the input json file.
	 * @return Returns the status (Success or reject) in a JSONObject format
	 */

	private JSONObject change(JSONObject json) {
		String id2 = json.getString("id");
		List <Bookings> requestList = new ArrayList <Bookings> ();
		Queue <Room> roomList = new LinkedList <Room> ();
		List <Bookings> tempBookings = new ArrayList <Bookings> ();
		
		for (Venue v : venueList) {
			requestList = new ArrayList <Bookings> ();
			for (Room r : v.getRoomList()) {
				tempBookings = r.cancelBooking(id2);
				if (!tempBookings.isEmpty()) {
					requestList.addAll(tempBookings);
					roomList.add(r);
				}
				
			}
			if (!requestList.isEmpty()) {
				JSONObject result1 = request(json);
				if (result1.getString("status").equals("success")) {
					return result1;
				} else {
					for (Bookings b : requestList) {
						roomList.remove().bookRoom(b);
					}
					result1.put("status", "rejected");
					return result1;
				}   			
			}
		}
		JSONObject result = new JSONObject ();
		result.put("status", "rejected");
		return result;
	}
	/**
	 * Cancel Function
	 * @param json The input JSON
	 */
    
	private void cancel(JSONObject json) {
		String id1 = json.getString("id");
		for (Venue iVenue : venueList) {
			for (Room iRoom : iVenue.getRoomList()) {
				iRoom.cancelBooking(id1);
			}
		}
	}
	

	/**
	 * This is the execution of the list command
	 * @param json This is the input json file.
	 * @return Returns the list of rooms with their respective list of reservations in a JSONArray format
	 */
    
    public JSONArray list(JSONObject json) {
    	JSONObject temp = new JSONObject();
    	JSONObject bookingTemp = new JSONObject();
    	JSONArray result = new JSONArray();
    	List<Bookings> bookingResults = new ArrayList<Bookings> ();
    	JSONArray sortedBookingResults = new JSONArray();
    	
    	Venue venue = getVenueByName(json.getString("venue")); 

    	for(Room room: venue.getRoomList()) {
    		sortedBookingResults = new JSONArray();
    		bookingResults = new ArrayList<Bookings> ();
    		temp = new JSONObject();
    		temp.put("room", room.getRoomName());
    		bookingResults.addAll(room.getBookingList());
    		Collections.sort(bookingResults, Bookings.BookingsDateComparator);
    		for (Bookings bookings: bookingResults) {
    			bookingTemp = new JSONObject();
    			bookingTemp.put("id", bookings.getId());
    			bookingTemp.put("start", bookings.getStart());
    			bookingTemp.put("end", bookings.getEnd());
    			sortedBookingResults.put(bookingTemp);
    		}

    		temp.put("reservations", sortedBookingResults);
    		result.put(temp);  		
    	}
    	return result;
    	
    	
    }
    
    /**
     * This is a method to get a venue by its name
     * @param venueName The name of the venue
     * @return A venue object
     */
    
    private Venue getVenueByName(String venueName) {
    	for (Venue temp:venueList) {
    		if (temp.getVenueName().equals(venueName)) {
    			return temp;
    		}
    	}
    	
    	Venue newVenue = new Venue(venueName);
    	venueList.add(newVenue);
		return newVenue;
    }


	public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                system.processCommand(command);
            }
        }
        sc.close();
    }

}
