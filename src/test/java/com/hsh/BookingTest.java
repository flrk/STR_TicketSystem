package com.hsh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
    private Customer customer;
    private CulturalEvent culturalEvent;
    private int bookedSeats;
    @BeforeEach
    void init(){
        customer = new Customer("Hans Meier", "Zeppelinweg 7, 3049 Hannover");
        culturalEvent = new CulturalEvent(1,"Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000, "info@promoter.de");
        bookedSeats = 2;
    }

    @Test
    void shouldCreateBooking(){
        Booking booking = new Booking(customer, culturalEvent, bookedSeats);
        assertNotNull(booking);
    }

    @Test
    void shouldRejectNullPointerForCulturalEvent(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(customer, null, bookedSeats));
    }

    @Test
    void shouldRejectBookedSeatsLowerOne(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(customer, culturalEvent, 0));
    }

    @Test
    void shouldRejectNullPointerForCustomer(){
        assertThrows(IllegalArgumentException.class, () -> new Booking(null, culturalEvent, bookedSeats));
    }

    @Test
    void getBookedSeats(){
        Booking booking = new Booking(customer, culturalEvent, bookedSeats);
        assertEquals(bookedSeats, booking.getBookedSeats());
    }
}
