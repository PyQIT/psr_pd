package models;

import java.io.Serializable;

public class Library implements Serializable {

    private String uniqueID;
    private String address;
    private int size;

    public Library(String uniqueID, String address, int size) {
        this.uniqueID = uniqueID;
        this.address = address;
        this.size = size;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setAddress(String address) {this.address = address;}

    public String getAddress() {return address;}

    public int getCapacity() {
        return size;
    }

    public void setCapacity(int capacity) {
        this.size = size;
    }


    @Override
    public String toString(){
        return "Library ID: " + uniqueID + " address: " + address + " Library size: " + size;
    }
}
