package org.example.model;

public class Student extends Person {

    private String contactInformation;

    public Student(int id, String name, String contactInformation) {
        super(id, name);
        this.contactInformation = contactInformation;
    }

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setContactInformation(String contactInformation) {this.contactInformation = contactInformation;}

    public int getId() {return this.id;}
    public String getName() {return this.name;}
    public String getContactInformation() {return this.contactInformation;}

}
