package org.example.repository;

import org.example.model.Room;
import java.util.ArrayList;
import java.util.List;


public class RoomRepository {
    private static RoomRepository instance;
    private List<Room> rooms = new ArrayList<>();

    private RoomRepository() {} // private constructor

    public static RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }
    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getAllRooms() {
        return rooms;
    }
}
