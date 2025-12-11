package org.example.model;

public class PracticeRoom extends Room {
    private String equipment;

    public PracticeRoom(int ID, int capacity, String equipment) {
        super(ID, capacity);
        this.equipment = equipment;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    @Override
    public String getRoomType() {
        return "Practice";
    }
}