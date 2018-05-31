package com.hsh;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class TicketSystem {
    private HashMap<Integer,CulturalEvent> culturalEventMap;
    private HashMap<String,Customer> customerMap;
    private HashMap<String,Booking> bookingMap;
    private int culturalEventID;

    public TicketSystem(){
       culturalEventMap = new HashMap<>();
       customerMap = new HashMap<>();
       bookingMap = new HashMap<>();
       culturalEventID = 0;
    }

    public void createNewCustomer(String name, String address){
        customerMap.put(name, new Customer(name, address));
    }

    public void createNewCulturalEvent(String name, LocalDateTime dateTime, double price, int totalNumberOfSeats){
        int culturalEventID = getCulturalEventID();
        culturalEventMap.put(culturalEventID,new CulturalEvent(culturalEventID,name,dateTime,price,totalNumberOfSeats));
    }

    public List<CulturalEvent> listAllCulturalEvents(){
        return (List<CulturalEvent>) new LinkedList<>(culturalEventMap.values()).clone();
    }

    public List<Customer> listAllCustomers(){return new LinkedList<>(customerMap.values());}

    public int getRemainingSeatsForCulturalEvent(int id){
        return culturalEventMap.get(id).getRemainingSeats();
    }

    public void createNewBookingForCustomer(String name, CulturalEvent culturalEvent, int bookedSeats){
        Customer requestedCustomer = customerMap.get(name);
        if(requestedCustomer == null){
            throw new NoSuchElementException();
        }
        if(culturalEvent.getRemainingSeats()< bookedSeats){
            throw new IllegalArgumentException("Nicht genug freie Plätze");
        }

        culturalEvent.decreaseRemainingSeats(bookedSeats);

        int alreadyBookedSeats = 0;
        if(bookingMap.containsKey(name+":"+culturalEvent.getEventID())){
            alreadyBookedSeats = bookingMap.get(name+":"+culturalEvent.getEventID()).getBookedSeats();
        }

        bookingMap.put(name+":"+culturalEvent.getEventID(), new Booking(requestedCustomer, culturalEvent, bookedSeats+alreadyBookedSeats));
    }

    public Booking getBooking(String name, CulturalEvent culturalEvent){
        return bookingMap.get(name+":"+culturalEvent.getEventID());
    }

    public boolean saveAllData(){
        boolean success = true;
        success = save(customerMap,"customers");
        success = save(bookingMap, "bookings");
        success = save(culturalEventMap, "culturalEvents");

        return success;
    }

    public void loadAllData(){
        customerMap = loadHashMap("customers");
        bookingMap = loadHashMap("bookings");
        culturalEventMap = loadHashMap("culturalEvents");
    }

    private HashMap loadHashMap(String filename){
        HashMap tmp = new HashMap();

        try(FileInputStream fis = new FileInputStream("persistence/"+filename+".ser");
        ObjectInputStream ois = new ObjectInputStream(fis)) {
            tmp = (HashMap) ois.readObject();
        }catch(Exception ioe){
                ioe.printStackTrace();
                return tmp;
        }
        return tmp;
    }

    private boolean save(Object objectToPersist, String filename){
        try(FileOutputStream fos = new FileOutputStream("persistence/"+filename+".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos) ){
            oos.writeObject(objectToPersist);
        }catch(IOException ioe){
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    private int getCulturalEventID(){
        return ++culturalEventID;
    }

    public void eraseTemporaryData() {
        customerMap.clear();
        bookingMap.clear();
        culturalEventMap.clear();
    }
}
