package org.example.repository;

import org.example.model.Room;
import org.example.factory.RoomFactory;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    private static RoomRepository instance;
    private List<Room> rooms;
    private int nextRoomID;

    // Private constructor (Singleton)
    private RoomRepository() {
        rooms = new ArrayList<>();
        nextRoomID = 1;
        initializeTestData();
    }

    // Initialize with test data using Factory
    private void initializeTestData() {
        rooms.add(RoomFactory.createRoom("PRACTICE", nextRoomID++, 2, "Piano"));
        rooms.add(RoomFactory.createRoom("PRACTICE", nextRoomID++, 4, "Guitar, Microphone"));
        rooms.add(RoomFactory.createRoom("LECTURE", nextRoomID++, 20, null));
    }

    // Get singleton instance
    public static synchronized RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    // Add room
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Remove room by ID
    public boolean removeRoom(int roomID) {
        return rooms.removeIf(r -> r.getRoomID() == roomID);
    }

    // Update room
    public void updateRoom(Room room) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomID() == room.getRoomID()) {
                rooms.set(i, room);
                break;
            }
        }
    }

    // Get all rooms
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    // Get room by ID
    public Room getRoomByID(int roomID) {
        return rooms.stream()
                .filter(r -> r.getRoomID() == roomID)
                .findFirst()
                .orElse(null);
    }

    // Generate new room ID
    public int generateRoomID() {
        return nextRoomID++;
    }
}