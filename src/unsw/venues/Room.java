package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Room class
 * @author Aven Au z5208734
 *
 */
public class Room {
	private String roomName;
	private String size;
	private List <Bookings> bookingList = new ArrayList <Bookings>();
	
	public Room () {
		
	}
	/**
	 * The constructor with room name and size
	 * @param roomName The name of the room
	 * @param size The size of the room
	 */
	public Room (String roomName, String size) {
		this.roomName = roomName;
		this.size = size;
	}
	
	/**
	 * Check if the Room is booked on that time period
	 * @param start The start date of the booking
	 * @param end The end date of the booking
	 * @param id The id of the booking
	 * @return Returns a boolean whether the room is avaliable.
	 */
	public boolean isAvaliable(LocalDate start, LocalDate end, String id) {
		boolean isFree = true;
		for(Bookings bookings : bookingList) {
			if ( (bookings.getStart().compareTo(end) > 0)  || (bookings.getEnd().compareTo(start) < 0)) {
				isFree = isFree && true;
			} else {
				isFree = isFree && false;
			}
		}
		return isFree;
	}
	
	/**
	 * Booking the room
	 * @param start The start date of the booking
	 * @param end The end date of the booking
	 * @param id The booking id of the booking
	 */
	public void bookRoom(LocalDate start, LocalDate end, String id) {
		bookingList.add(new Bookings(start, end, id));
	}
	
	/**
	 * Booking room with the booking class
	 * @param bookings The booking class
	 */
	public void bookRoom(Bookings bookings) {
		bookingList.add(bookings);
	}

	/**
	 * Get the room name of the current room
	 * @return Room name in String form
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * Setting the name of the Room Name
	 * @param roomName The room name
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * Get the size of the room
	 * @return Return the size of the room
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Set the size of the room
	 * @param size Size of the room
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * Getting the Booking Lists
	 * @return A list of bookings
	 */
	public List<Bookings> getBookingList() {
		return bookingList;
	}
	
	/**
	 * Cancel a booking
	 * @param id The id of the booking that is requested to be cancelled
	 * @return It returns all the bookings that are deleted.
	 */
	public List<Bookings> cancelBooking(String id) {
		Iterator <Bookings> iterator = bookingList.iterator();
		List<Bookings> cancelled = new ArrayList <Bookings>();
		
		while (iterator.hasNext()) {
			Bookings index = iterator.next();
			if (index.getId().equals(id)) {
				cancelled.add(index);
				iterator.remove();
			}
		}
		return cancelled;
	}
	

}
