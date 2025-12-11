package org.example.model;

public abstract class Room {
    private int roomId;
    private int capacity;


    // Constructor
    public Room(int roomID, int capacity) {
        this.roomId = roomID;
        this.capacity = capacity;
    }

    // Getters and Setters
    public int getRoomID() { return roomId; }
    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public abstract String getRoomType();
}