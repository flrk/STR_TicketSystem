package com.hsh;

import java.io.*;
import java.util.*;

public class CustomerRepository {

    public List<Customer> findAllCustomers() throws IOException {
        List<Customer> tmp;

        try(FileInputStream fis = new FileInputStream("persistence/customers.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            tmp = (List<Customer>) ois.readObject();
        }catch(Exception e){
            throw new IOException("Could not load data");
        }
        return tmp;
    }

    public void saveAllCustomers(List<Customer> customers) throws IOException {
        try(FileOutputStream fos = new FileOutputStream("persistence/customers.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos) ){
            oos.writeObject(customers);
        }catch(IOException ioe){
            throw new IOException("Could not save Data");
        }
    }


}
