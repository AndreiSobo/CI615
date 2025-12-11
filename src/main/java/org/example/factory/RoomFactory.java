package org.example.factory;

import org.example.model.Room;
import org.example.model.PracticeRoom;
import org.example.model.LectureRoom;

public class RoomFactory {

    // Static factory method
    public static Room createRoom(String roomType, int ID, int capacity, String equipment) {
        if (roomType == null) {
            return null;
        }

        if (roomType.equalsIgnoreCase("PRACTICE")) {
            return new PracticeRoom(ID, capacity, equipment);
        }
        else if (roomType.equalsIgnoreCase("LECTURE")) {
            return new LectureRoom(ID, capacity);
        }

        return null;
    }
}