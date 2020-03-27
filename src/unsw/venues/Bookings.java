package unsw.venues;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Booking class
 * @author Aven Au z5208734
 *
 */
public class Bookings implements Comparable<Bookings>{
	private LocalDate start;
	private LocalDate end;
	private String id;
	
	public Bookings() {
		
	}
	
	/**
	 * Constructor with a Booking class (Used to duplicate Bookings)
	 * @param another The booking class that you want to duplicate
	 */
	public Bookings(Bookings another) {
		this.start = another.getStart();
		this.end = another.getEnd();
		this.id = another.getId();
	}
	
	/**
	 * Constructor for bookings
	 * @param start The start date of the booking
	 * @param end The end date of the booking
	 * @param id The booking id of the booking
	 */
	public Bookings(LocalDate start, LocalDate end, String id) {
		this.start = start;
		this.end = end;
		this.id = id;
	}

	/**
	 * The start date of the booking
	 * @return The start date
	 */
	public LocalDate getStart() {
		return start;
	}

	/**
	 * Setting the start date
	 * @param start The start date
	 */
	public void setStart(LocalDate start) {
		this.start = start;
	}

	/**
	 * Getting the end date of the booking
	 * @return The end date of the booking
	 */
	public LocalDate getEnd() {
		return end;
	}

	/**
	 * Setting the end date of the booking
	 * @param end The end date of the booking
	 */
	public void setEnd(LocalDate end) {
		this.end = end;
	}

	/**
	 * Getting the Booking id of the booking
	 * @return The booking id
	 */
	public String getId() {
		return id;
	}

	/** 
	 * Setting the booking id
	 * @param id The booking id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Comparator so I could sort the booking via the dates.
	 */
	public static Comparator<Bookings> BookingsDateComparator = new Comparator<Bookings> () {
		public int compare(Bookings booking1, Bookings booking2) {
			return booking1.getStart().compareTo(booking2.getEnd());
		}
	};

	@Override
	public int compareTo(Bookings b) {
		// TODO Auto-generated method stub
		
		return this.getStart().compareTo(b.getStart());
	}

	
	
	

}
