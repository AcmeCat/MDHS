package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    
    //class constant
    public final static String PRODUCT_FILE = "product.ser"; //name of file for storing products on server
    
    //instance variables
    private String name;
    private double quantity;
    private String unit;
    private double unitPrice;
    private String ingredients;
   
    //parametised constructor
    public Product(String name, double quantity, String unit, double unitPrice, String ingredients) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.ingredients = ingredients;
    }
    
    public Product (Product data){
        this(   data.getName(),
                data.getQuantity(),
                data.getUnit(),
                data.getUnitPrice(),
                data.getIngredients());
    }
    
    public Product (String[] params){
        this.name = params[0];
        this.quantity = Double.parseDouble(params[2]);
        this.unit = params[1];
        this.unitPrice = Double.parseDouble(params[3]);
        this.ingredients = params[4];
    }
    
    //getters
    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    
    public String getIngredients() {
        return ingredients;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    
    //toString method
    @Override
    public String toString() {
        return  "\nProduct Name: " + name + 
                "\n- Quantity: " + quantity + unit + "s @ $" + unitPrice + " per " + unit + 
                "\n- Ingerdients: " + ingredients;
    }
}
