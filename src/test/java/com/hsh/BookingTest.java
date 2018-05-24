package com.hsh;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
    private Customer customer;
    @BeforeEach
    public void init(){
        customer = new Customer("Hans Meier", "Zeppelinweg 7, 3049 Hannover");
    }

    @Test
    public void shouldCreateBooking(){
        Booking booking = new Booking(customer, 1, 2);
        assertNotNull(booking);
    }

    @Test
    public void shouldRejectEventIdsLowerOne(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(customer, 0, 2));
    }

    @Test
    public void shouldRejectBookedSeatsLowerOne(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(customer, 2, 0));
    }

    @Test
    public void shouldRejectNullPointerForCustomer(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(null, 2, 2));
    }
}
