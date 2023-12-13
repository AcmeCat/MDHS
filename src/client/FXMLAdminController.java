/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import domain.Customer;
import domain.DeliverySchedule;
import domain.Product;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLAdminController implements Initializable {
    
    private MDHSClient client;
    private LinkedList<Customer> customers;
    private LinkedList<Product> products;
    private LinkedList<DeliverySchedule> schedules;

    @FXML
    private TextField txtName;
    
    @FXML
    private TextField txtQuantity;
    
    @FXML
    private TextField txtUnit;
    
    @FXML
    private TextField txtPrice;
    
    @FXML
    private TextField txtIngredients;
    
    @FXML
    private TextField txtPostCode;
    
    @FXML
    private TextField txtDay;
    
    @FXML
    private TextField txtCost;
    
    @FXML
    private TextArea tarAdminDisplay;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        client = MDHS.getClient();
        customers = new LinkedList();
        products = new LinkedList();
        schedules = new LinkedList();
    }   
    
    @FXML
    private void addProduct(){
        Product product = new Product(  txtName.getText(),
                                        Double.parseDouble(txtQuantity.getText()),
                                        txtUnit.getText(),
                                        Double.parseDouble(txtPrice.getText()),
                                        txtIngredients.getText());
        products.add(product);
    }
    
    @FXML
    private void submitProducts(){
        client.sendList(products, "addproducts");
    }
    
    @FXML
    private void addSchedule(){
        DeliverySchedule schedule = new DeliverySchedule(   txtPostCode.getText(),
                                                            txtDay.getText(),
                                                            Double.parseDouble(txtCost.getText()));
        schedules.add(schedule);
    }
    
    @FXML
    private void submitSchedules(){
        client.sendList(schedules, "addschedules");
    }
    
    @FXML
    private void showAllCustomers(){
        customers = client.getList("getcustomers");
        tarAdminDisplay.clear();
        tarAdminDisplay.setText("Customers:\n----------------------\n");
        customers.forEach(customer -> {
            tarAdminDisplay.appendText(customer.toString() + "\n");
        });
    }
    
    @FXML
    private void returnToMain () {
        MDHS.loadScene("FXMLMain");
    }
}
