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

public class FXMLCustomerController implements Initializable {

    private MDHSClient client;
    private LinkedList<Customer> customers;
    private LinkedList<Product> products;
    private LinkedList<DeliverySchedule> schedules;

    @FXML
    private TextArea tarCustomerInfo;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private TextField txtPhone;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtPassword;
    
    @FXML
    private TextField txtAddress;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        client = MDHS.getClient();
        customers = new LinkedList();
        products = new LinkedList();
        schedules = new LinkedList();
    }

    @FXML //constructs customer from textfeilds and adds it to customers linkedlist
    private void addCustomer() {
        Customer customer = new Customer(   txtName.getText(),
                                            txtPhone.getText(),
                                            txtEmail.getText(),
                                            txtPassword.getText(),
                                            txtAddress.getText());
        customers.add(customer);
    }
    
    @FXML
    private void submitAllCustomers() {
        client.sendList(customers, "addcustomers");
    }
    
    @FXML
    private void displaySchedules () {
        schedules = client.getList("getschedules");
        tarCustomerInfo.clear();
        tarCustomerInfo.setText("Delivery Schedules:\n----------------------\n");
        schedules.forEach(schedule -> {
            tarCustomerInfo.appendText(schedule.toString() + "\n");
        });
    }
    
    @FXML
    private void displayProducts () {
        products = client.getList("getproducts");
        tarCustomerInfo.clear();
        tarCustomerInfo.setText("Products:\n----------------------\n");
        products.forEach(product -> {
            tarCustomerInfo.appendText(product.toString() + "\n");
        });
    }
    
    @FXML
    private void returnToMain () {
        MDHS.loadScene("FXMLMain");
    }
}
