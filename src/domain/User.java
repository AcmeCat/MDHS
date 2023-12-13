package domain;

import java.io.Serializable;

public class User implements Serializable{
    //instance variable
    private String name;

    //parametised constructor
    public User(String name) {
        this.name = name;
    }

    //getter
    public String getName() {
        return name;
    }
    
    //setter
    public void setName(String name) {
        this.name = name;
    }
    
    
}
