package entities;

import enums.Level;

public class Customer {

    private final String customerID;
    private final String FirstName;
    private final String LastName;
    private String Email;
    private String Phone;
    private String Address;
    private Level level;

    public Customer(String customerID, String FirstName, String LastName
            , String Phone, String Address, Level level) {
        this.customerID = customerID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
        this.Address = Address;
        this.level = level;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
