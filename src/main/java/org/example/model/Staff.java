package org.example.model;

public class Staff extends Person {

    private String role;

    public Staff(int id, String name, String role) {
        super(id, name);
        this.role = role;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getRole() {return role;}

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setRole(String role) {this.role = role;}
}
