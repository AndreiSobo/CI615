package org.example.model;

import java.time.LocalTime;
import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private int roomId;
    private int studentID;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private long duration;  // derived attribute - calculated
    private int nrOfParticipants;
    private boolean isRecurrent;

    public Booking(int bookingId, int roomId, int studentID, LocalDate date, LocalTime startTime, LocalTime endTime,
                   int nrOfParticipants, boolean isRecurrent) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.studentID = studentID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = java.time.Duration.between(this.startTime, this.endTime).toMinutes();
        this.nrOfParticipants = nrOfParticipants;
        this.isRecurrent = isRecurrent;
    }

    public int getBookingID() { return bookingId; }
    public int getRoomID() { return roomId; }
    public int getStudentID() { return studentID; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public int getNrOfParticipants() { return nrOfParticipants; }
    public boolean isRecurrent() { return isRecurrent; }

    public void setDate(LocalDate date) { this.date = date; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setNrOfParticipants(int nrOfParticipants) {this.nrOfParticipants = nrOfParticipants; }
    public void setRecurrent(boolean isRecurrent) { this.isRecurrent = isRecurrent; }
}
