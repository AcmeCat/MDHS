/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import domain.DeliverySchedule;
import domain.Product;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author allen
 */
public class FXMLMainController implements Initializable {
    
    private final LinkedList<DeliverySchedule> DEFAULT_SCHEDULES = new LinkedList();
    private MDHSClient client;

    @FXML
    private TextArea tarMainLoading;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //connect to server
        client = MDHS.getClient();
        tarMainLoading.appendText(client.connect());
        //load default product list from csv to file on server
        LinkedList<Product> productList = loadDefaultProductsFromFile("products.csv");
        tarMainLoading.appendText("\nLoading default products from file..."
                + "\nProducts loaded: ");
        productList.forEach(p -> {
            tarMainLoading.appendText("\n -- " + p.getName() + ".");
        });
        client.sendList(productList, "addproducts");
        tarMainLoading.appendText("\ndefault products loaded to server");
        //load default schedule list to server
        DEFAULT_SCHEDULES.add(new DeliverySchedule("4550", "Monday", 7));
        DEFAULT_SCHEDULES.add(new DeliverySchedule("4551", "Tuesday", 4));
        DEFAULT_SCHEDULES.add(new DeliverySchedule("4552", "Wednesday", 5));
        DEFAULT_SCHEDULES.add(new DeliverySchedule("4553", "Thursday", 6));
        DEFAULT_SCHEDULES.add(new DeliverySchedule("4554", "Friday", 8));
        tarMainLoading.appendText("\nLoading default delivery schedules from settings..."
                + "\nSchedules loaded: ");
        DEFAULT_SCHEDULES.forEach(ds -> {
            tarMainLoading.appendText("\n -- " + ds.getPostCode() + " on " + ds.getDay() + ".");
        });
        client.sendList(DEFAULT_SCHEDULES, "addschedules");
        tarMainLoading.appendText("\ndefault delivery schedules loaded to server.");
        
    }    
    
    @FXML
    public void openCustomerScene(){
        MDHS.loadScene("FXMLCustomer");
    }
    
    @FXML
    public void openAdminScene(){
        MDHS.loadScene("FXMLAdmin");
    }
    
    //returns linked list of product objects parsed from a .csv file
    public LinkedList<Product> loadDefaultProductsFromFile (String file) {
        LinkedList<Product> products = new LinkedList();
        Path path = Paths.get(file);
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.US_ASCII);
            String nextLine = reader.readLine();
            while (nextLine != null){
                nextLine = reader.readLine();
                String[] params = nextLine.split(",");
                products.add(new Product(params));
                
            }
        } catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        
        System.out.println("read it all");
        return products;
    }
}
