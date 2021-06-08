public class Cinema {

    private String uniqueID;
    private String address;

    public Cinema(String uniqueID, String address) {
        this.uniqueID = uniqueID;
        this.address = address;
    }

    public String getUniqueID() {
        return uniqueID;
    }
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setAddress(String address) {this.address = address;}

    public String getAddress() {return address;}



    @Override
    public String toString(){
        return " Cinema ID: " + uniqueID + " Address: " + address;
    }
}
