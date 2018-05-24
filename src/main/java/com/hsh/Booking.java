package com.hsh;

public class Booking {
    private Customer customer;
    private CulturalEvent culturalEvent;
    private int bookedSeats;


    public Booking(Customer customer, CulturalEvent culturalEvent, int bookedSeats){
        setCustomer(customer);
        setCulturalEvent(culturalEvent);
        setBookedSeats(bookedSeats);
    }

    private void setCulturalEvent(CulturalEvent culturalEvent){
        if(culturalEvent == null){
            throw new IllegalArgumentException();
        }
        this.culturalEvent = culturalEvent;
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

    int getBookedSeats(){
        return bookedSeats;
    }
}
