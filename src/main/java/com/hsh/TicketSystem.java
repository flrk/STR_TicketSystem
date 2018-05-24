package com.hsh;

import java.time.LocalDateTime;
import java.util.*;

public class TicketSystem {
    private final HashMap<Integer,CulturalEvent> culturalEventMap;
    private final HashMap<String, Customer> customerMap;
    private int culturalEventID;

    public TicketSystem(){
       culturalEventMap = new HashMap<>();
       customerMap = new HashMap<>();
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

    public void createNewBookingForCustomer(String name, int eventId, int bookedSeats){
        Customer requestedCustomer = customerMap.get(name);
        if(requestedCustomer ==  null){
            throw new NoSuchElementException();
        }
        Booking booking = new Booking(requestedCustomer, eventId, bookedSeats);

    }

    private int getCulturalEventID(){
        return ++culturalEventID;
    }
}
