package org.example.model;

public class LectureRoom extends Room {
    private boolean isSoundproof;

    public LectureRoom(int ID, int capacity, boolean isSoundproof) {
        super(ID, capacity);
        this.isSoundproof = isSoundproof;
    }

    // If you don't want extra attributes, simplify:
    public LectureRoom(int ID, int capacity) {
        super(ID, capacity);
        this.isSoundproof = false; // default
    }

    public boolean isSoundproof() {
        return isSoundproof;
    }

    public void setSoundproof(boolean soundproof) {
        isSoundproof = soundproof;
    }

    @Override
    public String getRoomType() {
        return "Lecture";
    }
}