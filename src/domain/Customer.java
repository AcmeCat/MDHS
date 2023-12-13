/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author allen
 */
public class Customer extends User {
    
    //class constant
    public final static String CUSTOMER_FILE = "customer.ser"; //name of file for storing customers on server
    
    //instance variables
    private String  phoneNumber,
                    email,
                    password,
                    address;
  
    //parametised constructor
    public Customer(String name, String phoneNumber, String email, String password, String address) {
        super(name);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.address = address;
    }
    
    public Customer (Customer data){
        this(   data.getName(),
                data.getPhoneNumber(),
                data.getEmail(),
                data.getPassword(),
                data.getAddress());
    }
    
    //getters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }
    
    
    //setters
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    

    //toString method
    @Override
    public String toString() {
        return  "\nCustomer name: " + getName() + 
                "\n- Phone Number: " + phoneNumber + 
                "\n- Email Address: " + email + 
                "\n- Password: " + password + 
                "\n- Address: " + address + '}';
    }
}
