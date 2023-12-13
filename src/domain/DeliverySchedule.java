/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author allen
 */
public class DeliverySchedule implements Serializable {
    
    //class constant
    public final static String SCHEDULE_FILE = "schedule.ser"; //name of file for storing delivery schedules on server
    
    //instance variables
    private String postCode;    //validation should restrict length and enforce numerical. String used as no numerical operation required
    private String day;         //should be enum
    private double cost;        //in $ value. restrict display to 2dp

    public DeliverySchedule(String postCode, String day, double cost) {
        this.postCode = postCode;
        this.day = day;
        this.cost = cost;
    }
    
    public DeliverySchedule (DeliverySchedule data){
        this(   data.getPostCode(),
                data.getDay(),
                data.getCost());
    }

    //getters
    public String getPostCode() {
        return postCode;
    }

    public String getDay() {
        return day;
    }

    public double getCost() {
        return cost;
    }
    
    //setters
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    //toString method
    @Override
    public String toString() {
        return  "\n" + day + "'s delivery to areas with postcode: " + postCode + "." + 
                "\n- (Cost: $" + cost + ")";
    }
}
