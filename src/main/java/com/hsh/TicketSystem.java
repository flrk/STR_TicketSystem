package com.hsh;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class TicketSystem {
    private BlacklistService bs;
    private HashMap<Integer,CulturalEvent> culturalEventMap;
    private HashMap<String,Customer> customerMap;
    private HashMap<String,Booking> bookingMap;
    private int culturalEventID;

    public TicketSystem(BlacklistService bs){
        this.bs = bs;
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

    public void createNewBookingForCustomer(String name, CulturalEvent culturalEvent, int bookedSeats) throws CustomerRejectedException {
        Customer requestedCustomer = customerMap.get(name);
        if(requestedCustomer == null){
            throw new NoSuchElementException();
        }
        if(bs.isCustomerOnBlacklist(requestedCustomer)){
            throw new CustomerRejectedException("Dem kunden sind keine Buchungen erlaubt");
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
        try{
            new CustomerRepository().saveAllCustomers(new LinkedList(customerMap.values()));
            new BookingRepository().saveAllBookings(new LinkedList(bookingMap.values()));
            new CulturalEventRepository().saveAllCulturalEvents(new LinkedList(culturalEventMap.values()));

        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void loadAllData(){

        try{
            customerMap = convertCustomerCollectionToMap(new CustomerRepository().findAllCustomers());
            bookingMap = convertBookingCollectionToMap(new BookingRepository().findAllBookings());
            culturalEventMap = convertCulturalEventCollectionToMap(new CulturalEventRepository().findAllCulturalEvents());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private HashMap<String, Customer> convertCustomerCollectionToMap(List<Customer> input){

        HashMap<String,Customer> customers = new HashMap<>();
        for (Customer c: input) {
            customers.put(c.getName(), c);
        }
        return customers;
    }

    private HashMap<String, Booking> convertBookingCollectionToMap(List<Booking> input){

        HashMap<String,Booking> bookings = new HashMap<>();
        for (Booking b: input) {
            bookings.put(b.getId(), b);
        }
        return bookings;
    }

    private HashMap<Integer, CulturalEvent> convertCulturalEventCollectionToMap(List<CulturalEvent> input){

        HashMap<Integer,CulturalEvent> tmp = new HashMap<>();
        for (CulturalEvent ce: input) {
            tmp.put(ce.getEventID(), ce);
        }
        return tmp;
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
