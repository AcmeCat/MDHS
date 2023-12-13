package server;

import domain.Customer;
import domain.DeliverySchedule;
import domain.Product;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class MDHSServer {
    
//instance variables for portnumber, server socket, client socket
    private final int PORT_NUMBER = 8888;
    private final ServerSocket serverSocket;
    private Socket clientSocket;
   
    //constructor - initialize serversocket
    public MDHSServer () throws IOException { //catch or propogate?
        System.setProperty("java.net.preferIPv4Stack" , "true"); //needed?
        serverSocket = new ServerSocket(PORT_NUMBER);
    }
    
    public void addThreadForClient () throws IOException {
        System.out.println("waiting for client...");
        while(true) {//todo: add timeout
            clientSocket = serverSocket.accept();
            Connection connection = new Connection(clientSocket);
        }
    }
    
    public static void main (String args[]){
        try{
            MDHSServer server = new MDHSServer();
            server.addThreadForClient();
        } catch (IOException e){
            System.out.println("error");
        }
    } 
}

class Connection extends Thread {
    
    Socket clientSocket;
    ObjectInputStream oIS;
    ObjectOutputStream oOS;
    
    //instance variables for 3 lists
    private LinkedList<Customer> customerList;
    private LinkedList<Product> productList;
    private LinkedList<DeliverySchedule> scheduleList;
    
    //instance variable for datamanager and filename/s
    private DataManager dataManager;
    
    public Connection(Socket newClientSocket) {
        customerList = new LinkedList();
        productList = new LinkedList();
        scheduleList = new LinkedList();
        
        dataManager = new DataManager();
        
        try {
            clientSocket = newClientSocket;
            oIS = new ObjectInputStream(clientSocket.getInputStream());
            oOS = new ObjectOutputStream(clientSocket.getOutputStream());
            this.start();
            System.out.println("a new thread!");
        } catch (IOException e) { //should probably propogate
            System.out.println("IO error: " + e.getLocalizedMessage());
        }
    } 
    
    @Override
    public void run(){
        try {
            while (true){
                String request = (String) oIS.readObject();
                if (request.equalsIgnoreCase("addcustomers")){
                    while (true){
                        Object data = oIS.readObject();
                        System.out.println(data);
                        try {
                            String stopSignal = (String) data;
                            if (stopSignal.equalsIgnoreCase("stop")){break;}
                        } catch (ClassCastException e){
                            System.out.println(e.getLocalizedMessage());
                        }
                        try {
                            Customer customer = (Customer) data;
                            customerList.add(customer);
                        } catch (ClassCastException e){
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                    if (customerList.size()>0){
                        dataManager.writeListToFile(customerList, Customer.CUSTOMER_FILE);
                        customerList.clear();
                    }
                } else if (request.equalsIgnoreCase("addproducts")){
                    while (true){
                        Object data = oIS.readObject();
                        System.out.println(data);
                        try {
                            String stopSignal = (String) data;
                            if (stopSignal.equalsIgnoreCase("stop")){break;}
                        } catch (ClassCastException e){
                            System.out.println(e.getLocalizedMessage());
                        }
                        try {
                            Product product = (Product) data;
                            productList.add(product);
                        } catch (ClassCastException e){
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                    if (productList.size()>0){
                        dataManager.writeListToFile(productList, Product.PRODUCT_FILE);
                        productList.clear();
                    }
                } else if (request.equalsIgnoreCase("addschedules")){
                    while (true){
                        Object data = oIS.readObject();
                        System.out.println(data);
                        try {
                            String stopSignal = (String) data;
                            if (stopSignal.equalsIgnoreCase("stop")){break;}
                        } catch (ClassCastException e){
                            System.out.println(e.getLocalizedMessage());
                        }
                        try {
                            DeliverySchedule schedule = (DeliverySchedule) data;
                            scheduleList.add(schedule);
                        } catch (ClassCastException e){
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                    if (scheduleList.size()>0){
                        dataManager.writeListToFile(scheduleList, DeliverySchedule.SCHEDULE_FILE);
                        scheduleList.clear();
                    }
                } else if (request.equalsIgnoreCase("getcustomers")){
                    customerList = dataManager.readListFromFile(Customer.CUSTOMER_FILE);
                    while (customerList.size()>0){oOS.writeObject(customerList.removeFirst());}
                    oOS.writeObject("stop");
                } else if (request.equalsIgnoreCase("getproducts")){
                    productList = dataManager.readListFromFile(Product.PRODUCT_FILE);
                    while (productList.size()>0){oOS.writeObject(productList.removeFirst());}
                    oOS.writeObject("stop");
                } else if (request.equalsIgnoreCase("getschedules")){
                    scheduleList = dataManager.readListFromFile(DeliverySchedule.SCHEDULE_FILE);
                    while (scheduleList.size()>0){oOS.writeObject(scheduleList.removeFirst());}
                    oOS.writeObject("stop");
                }
            }
        }catch (IOException | ClassNotFoundException e){
            System.out.println("error!");//remove later
        }
    }
}
