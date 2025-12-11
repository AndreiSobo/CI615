package org.example.controller;

import org.example.model.Booking;
import org.example.model.Room;
import org.example.repository.BookingRepository;
import org.example.repository.RoomRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private BookingRepository bookingRepo;
    private RoomRepository roomRepo;

    @Override
    public void init() {
        bookingRepo = BookingRepository.getInstance();
        roomRepo = RoomRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            request.setAttribute("bookings", bookingRepo.getAllBookings());
            request.setAttribute("rooms", roomRepo.getAllRooms());
            request.getRequestDispatcher("/bookings.jsp")
                    .forward(request, response);
        } else {
            request.setAttribute("rooms", roomRepo.getAllRooms());
            request.getRequestDispatcher("/book.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // Extract parameters
            int roomID = Integer.parseInt(request.getParameter("roomID"));
            int studentID = Integer.parseInt(request.getParameter("studentID"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
            int nrOfParticipants = Integer.parseInt(request.getParameter("nrOfParticipants"));
            boolean isRecurrent = request.getParameter("isRecurrent") != null;

            // Generate booking ID
            int bookingID = bookingRepo.generateBookingID();

            // Create booking
            Booking newBooking = new Booking(bookingID, roomID, studentID, date,
                    startTime, endTime, nrOfParticipants, isRecurrent);
            bookingRepo.addBooking(newBooking);

            // If recurring, create next week's booking
            if (isRecurrent) {
                int nextBookingID = bookingRepo.generateBookingID();
                LocalDate nextWeekDate = date.plusDays(7);
                Booking recurringBooking = new Booking(nextBookingID, roomID, studentID,
                        nextWeekDate, startTime, endTime,
                        nrOfParticipants, false); // Set to false
                bookingRepo.addBooking(recurringBooking);
            }

            response.sendRedirect(request.getContextPath() +
                    "/booking?action=list&success=booked");

        } else if ("cancel".equals(action)) {
            int bookingID = Integer.parseInt(request.getParameter("bookingID"));
            bookingRepo.removeBooking(bookingID);

            response.sendRedirect(request.getContextPath() +
                    "/booking?action=list&success=cancelled");
        }
    }
}