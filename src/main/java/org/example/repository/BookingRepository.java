package org.example.repository;

import org.example.model.Booking;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingRepository {
    private static BookingRepository instance;
    private List<Booking> bookings;
    private int nextBookingID;

    // Private constructor (Singleton pattern)
    private BookingRepository() {
        bookings = new ArrayList<>();
        nextBookingID = 1;
    }

    // Get singleton instance
    public static synchronized BookingRepository getInstance() {
        if (instance == null) {
            instance = new BookingRepository();
        }
        return instance;
    }

    // Add booking
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    // Remove booking by ID
    public boolean removeBooking(int bookingID) {
        return bookings.removeIf(b -> b.getBookingID() == bookingID);
    }

    // Update booking
    public void updateBooking(Booking booking) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingID() == booking.getBookingID()) {
                bookings.set(i, booking);
                break;
            }
        }
    }

    // Get booking by ID
    public Booking getBookingByID(int bookingID) {
        return bookings.stream()
                .filter(b -> b.getBookingID() == bookingID)
                .findFirst()
                .orElse(null);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings); // Return copy for safety
    }

    // Find bookings by student ID
    public List<Booking> findBookingsByStudent(int studentID) {
        return bookings.stream()
                .filter(b -> b.getStudentID() == studentID)
                .collect(Collectors.toList());
    }

    // Find bookings by room ID
    public List<Booking> findBookingsByRoom(int roomID) {
        return bookings.stream()
                .filter(b -> b.getRoomID() == roomID)
                .collect(Collectors.toList());
    }

    // Generate new booking ID
    public int generateBookingID() {
        return nextBookingID++;
    }
}