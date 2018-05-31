package com.hsh;

import java.io.Serializable;
import java.util.Objects;

public class Booking implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookedSeats == booking.bookedSeats &&
                Objects.equals(customer, booking.customer) &&
                Objects.equals(culturalEvent, booking.culturalEvent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(customer, culturalEvent, bookedSeats);
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
