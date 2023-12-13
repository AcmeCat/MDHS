package client;

import domain.Customer;
import domain.DeliverySchedule;
import domain.Product;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MDHSClient {
    
    //instance variables - socket, port number, ois, oos,
    //  linkedList<Product>, linkedList<DeliverySchedule>, linkedList<Customers>
    
    private final String host;
    private final int serverPortNumber;
    private Socket socket;
    private ObjectInputStream oIS;
    private ObjectOutputStream oOS;
    

    
    //constructor (no params) throws IOException - creates socket, connects to server socket, sets ois and oos
    public MDHSClient(){
        this.host = "localhost";
        this.serverPortNumber = 8888;
    }
    
    public String connect(){
        String result = "attempting connection to MDHSServer\n";
        try {
            socket = new Socket(host, serverPortNumber);
            oOS = new ObjectOutputStream(socket.getOutputStream());
            oIS = new ObjectInputStream(socket.getInputStream());
            result += "Connection Successful";
        } catch (IOException e) {
            System.out.println("IOException: " + e.getLocalizedMessage());
            result += "Connection Refused"; //TODO Exit Program
        }
        return result;
    }
    
    
    
    
    //generic method to send linkedLists to server. begins by sending signal string
    //  e.g. "addcustomers"), followed by each object in list, followed by closing signal (e.g. "stop")
    public <T> void sendList (LinkedList<T> list, String request){
        try {
            oOS.writeObject(request);
            while(list.size()>0){
                T listItem = list.removeFirst();
                System.out.println(listItem);
                oOS.writeObject(listItem);
            }
            oOS.writeObject("stop");
        } catch (IOException e){
            System.out.println("error");//implement
        }
    }
    
    //generic method to return object lists from server. begins with sending signal string
    //  e.g. "getcustomers" through oos, then reads returned objects from ois and appends them to ArrayList
    public <T> LinkedList<T> getList (String request){
        LinkedList list = new LinkedList();
        T returnedItem;
        try {
            oOS.writeObject(request);
            while (true){
                Object data = oIS.readObject();
                System.out.println("client: data - " + data);
                try {
                    String stopSignal = (String) data;
                    if (stopSignal.equalsIgnoreCase("stop")){
                    System.out.println("this is the end");
                    break;
                    }
                } catch (ClassCastException e){
                    System.out.println("cant cast to String, duh");
                }
                try {
                    returnedItem = (T) data;
                    list.add(returnedItem);
                } catch (ClassCastException e){
                    System.out.println("cant cast to Customer, duh");
                }
            }
        } catch (IOException e){
            System.out.println("Error: Input/Output");//implement
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: Class not found");
        }
        System.out.println("client: " + list);
        return list;
    }

}
