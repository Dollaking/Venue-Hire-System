package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the Venue class
 * @author Aven Au z5208734
 *
 */
public class Venue {
	private String venueName;
	private List <Room> roomList = new ArrayList <Room>();
	
	public Venue() {
		
	}
	
	/**
	 * 
	 * @param venueName The venue name 
	 */
	public Venue(String venueName) {
		this.setVenueName(venueName);
	}
	
	/**
	 * This is whether a booking is made
	 * @param id The booking id
	 * @param start The start date of booking
	 * @param end The end date of the booking
	 * @param small Amount of small rooms requested for booking
	 * @param medium Amount of medium rooms requested for booking
	 * @param large Amount of large rooms requested for booking
	 * @return
	 */
    public List<String> makeReservation(String id, LocalDate start, LocalDate end, int small, int medium, int large) {
    	List <Room> roomsToBook = new ArrayList <Room> ();
    	for (Room room : roomList) {
    		
    		if ((small + medium + large) <= 0 ) {
    			break;
    		}
    		if ( (room.getSize().equals("small") && small <= 0) || (room.getSize().equals("medium") && medium <= 0) || (room.getSize().equals("large") && large <= 0)){
    			continue;
    		}
    		if ( room.isAvaliable(start, end, id)) {
    			roomsToBook.add(room);
    			if (room.getSize().equals("small")) {
    				small--;
    			} else if (room.getSize().equals("medium")) {
    				medium--;
    			} else if (room.getSize().equals("large")) {
    				large--;
    			}
    		}
    	}
    	if ((small + medium + large) > 0) {
    		return new ArrayList <String>();
    	}
    	List<String> result = new ArrayList <String> (); 
    	for (Room iterate : roomsToBook) {
    		iterate.bookRoom(start,end,id);
    		result.add(iterate.getRoomName());
    	}
    	
    	return result; 	
    }
	
    /**
     * Get the venue name
     * @return Venue name in String format
     */
	public String getVenueName() {
		return venueName;
	}

	/**
	 * Setting Venue Name
	 * @param venueName Venue name you want to name it as
	 */
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	/**
	 * Get all the rooms in the venue
	 * @return Return a list of all the rooms in the Venue
	 */
	public List<Room> getRoomList(){
		return roomList;
		
	}
	
	/**
	 * Add a room into the Venue class
	 * @param room The room name
	 * @param size The size of the room
	 * @return Whether adding the room was successful
	 */
	public boolean addRoom(String room, String size) {
		roomList.add(new Room(room, size));
		return true;
	}
	
	/**
	 * Get Room with the Room Name
	 * @param roomName The name of the room that you want to find
	 * @return The room that is requested
	 */
	public Room getRoomByName(String roomName) {
		for (Room room : roomList) {
			if (room.getRoomName().equals(roomName)) {
				return room;
			}
		}
		return null;
	}
	
	
	
	

}
