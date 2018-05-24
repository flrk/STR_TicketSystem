package com.hsh;

public class Booking {
    private Customer customer;
    private int eventID;
    private int bookedSeats;


    public Booking(Customer customer, int eventID, int bookedSeats){
        setCustomer(customer);
        setEventID(eventID);
        setBookedSeats(bookedSeats);
    }

    private void setEventID(int eventID){
        if(eventID < 1){
            throw new IllegalArgumentException();
        }
        this.eventID = eventID;
    }

    private void setBookedSeats(int bookedSeats){
        if(bookedSeats < 1){
            throw new IllegalArgumentException();
        }
        this.bookedSeats = bookedSeats;
    }

    private void setCustomer(Customer customer){
        if(customer == null){
            throw new IllegalArgumentException();
        }
        this.customer = customer;
    }
}
