package models;

public class Borrow {

    private String title;
    private String autor;
    private String bookNumber;
    private String name;
    private String surname;
    private String pesel;
    private int phoneNumer;
    private String uniqueID;
    private String address;
    private int size;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {this.surname = surname;}

    public String getSurname() {return surname;}

    public void setPesel(String pesel) {this.pesel = pesel;}

    public String getPesel() {return pesel;}

    public int getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(int phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    public String getTitle() {
        return title;
    }

    public void setTile(String title) {
        this.title = title;
    }

    public void setAutor(String autor) {this.autor = autor;}

    public String getAutor() {return autor;}

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    @Override
    public String toString(){
        return  "Borrow - Client name: " + name + " Client surname: " + surname + " Client PESEL: " + pesel +
                " Client phone number: " + phoneNumer + " Library name: " + uniqueID + " Library address: " + address +
                " Library size: " + name + " Book title: " + title + " Book author: " + autor;
    }

}
