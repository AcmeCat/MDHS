package server;

import domain.Product;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {
    private ObjectInputStream oIS;
    private ObjectOutputStream oOS;
    private FileInputStream fIS;
    private FileOutputStream fOS;
    
    public DataManager(){}
    
    public <T> void writeListToFile (LinkedList<T> list, String fileName) {
        System.out.println("Hi. I am writing a file for you D;");
        try {
            File file = new File(fileName);
            boolean fileExists = file.exists();
            fOS = new FileOutputStream(fileName, true);
            if (fileExists){
                System.out.println("datamanger: appending");
                oOS = new AppendingObjectOutputStream(fOS);
            } else {
                System.out.println("datamanager: writing");
                oOS = new ObjectOutputStream(fOS);
            }
            for (T item:list){
                oOS.writeObject(item);
            }
            oOS.flush();
            oOS.close();
            System.out.println("written on chicken");
        }catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    public <T> LinkedList <T> readListFromFile (String fileName){
        LinkedList list = new LinkedList();
        try{
            if(!(new File(fileName).exists())){
            return null;
            }
            System.out.println("Datamanager: do we make it here?");
            
            fIS = new FileInputStream(fileName);
            oIS = new ObjectInputStream(fIS);
            
            
            while(fIS.available()>0){
                try{
                    list.add((T)oIS.readObject());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //list.add(new Product("test", 1.1, "testes", 0.0));
        return list;
    }
    
    //class AppendingOOS extends OOS
    //does not write header to objects
    final class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream os) throws IOException {
            super(os);
            this.writeStreamHeader();
        }
        @Override
        protected void writeStreamHeader() throws IOException {
        }
    }
}
