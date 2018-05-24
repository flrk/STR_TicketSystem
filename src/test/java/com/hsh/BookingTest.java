package com.hsh;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
    private Customer customer;
    private CulturalEvent culturalEvent;
    @BeforeEach
    void init(){
        customer = new Customer("Hans Meier", "Zeppelinweg 7, 3049 Hannover");
        culturalEvent = new CulturalEvent(1,"Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
    }

    @Test
    void shouldCreateBooking(){
        Booking booking = new Booking(customer, culturalEvent, 2);
        assertNotNull(booking);
    }

    @Test
    void shouldRejectNullPointerForCulturalEvent(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(customer, null, 2));
    }

    @Test
    void shouldRejectBookedSeatsLowerOne(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(customer, culturalEvent, 0));
    }

    @Test
    void shouldRejectNullPointerForCustomer(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(null, culturalEvent, 2));
    }

    @Test
    void getBookedSeats(){
        Booking booking = new Booking(customer, culturalEvent, 2);
        assertEquals(2, booking.getBookedSeats());
    }
}
