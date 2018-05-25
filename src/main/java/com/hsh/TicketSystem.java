package com.hsh;

import java.time.LocalDateTime;
import java.util.*;

public class TicketSystem {
    private final HashMap<Integer,CulturalEvent> culturalEventMap;
    private final HashMap<String,Customer> customerMap;
    private final HashMap<String,Booking> bookingMap;
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

    private int getCulturalEventID(){
        return ++culturalEventID;
    }
}
