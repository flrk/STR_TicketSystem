package com.hsh;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TicketSystemTest {

    private TicketSystem system;
    private Customer customer;
    private CulturalEvent culturalEvent;

    @BeforeEach
    void init(){
        system = new TicketSystem();
        customer = new Customer("Hans Meier", "Zechenweg 7, 30499 Hannover");
        system.createNewCustomer("Hans Meier", "Zechenweg 7, 30499 Hannover");
        system.createNewCulturalEvent("Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
        culturalEvent = new CulturalEvent(1,"Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
    }

    @Test
    void shouldCreateNewTicketSystem(){
        assertNotNull(system);
    }

    @Test
    void shouldCreateNewCustomer(){
        system.createNewCustomer("Hans Meier", "Zechenweg 7, 30499 Hannover");
    }

    @Test
    void shouldCreateNewCulturalEvent(){
        system.createNewCulturalEvent("Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
    }

    @Test
    void shouldListAllCulturalEvents(){
        LinkedList<CulturalEvent> listToCompare = new LinkedList<>();
        listToCompare.add(new CulturalEvent(1,"Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000));
        listToCompare.add(new CulturalEvent(2,"Der Ring der Niebelung", LocalDateTime.of(2018, Month.MARCH, 15, 19, 30), 24, 2_000));

        system.createNewCulturalEvent("Der Ring der Niebelung", LocalDateTime.of(2018, Month.MARCH, 15, 19, 30), 24, 2_000);

        List<CulturalEvent> culturalEventList = system.listAllCulturalEvents();

        assertTrue(culturalEventList.equals(listToCompare));
    }

    @Test
    void shouldListAllCustomers(){
        LinkedList<Customer> listToCompare = new LinkedList<>();
        listToCompare.add(new Customer("Hans Meier", "Zechenweg 7, 30499 Hannover"));
        listToCompare.add(new Customer("Hans Mayer", "Blabal Str. 4, 99999 Nonsenshausen"));
        listToCompare.add(new Customer("Hans Müller", "TickTack Str. 4, 99999 Nonsenshausen"));

        system.createNewCustomer("Hans Mayer", "Blabal Str. 4, 99999 Nonsenshausen");
        system.createNewCustomer("Hans Müller", "TickTack Str. 4, 99999 Nonsenshausen");

        List<Customer> customerList = system.listAllCustomers();

        assertTrue(customerList.equals(listToCompare));

    }

    @Test
    void shouldListRemainingSeatsForGivenCulturalEvent(){
        system.createNewCulturalEvent("Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);

        int remainingSeats = system.getRemainingSeatsForCulturalEvent(1);
        assertEquals(20_000, remainingSeats);

    }

    @Test
    void shouldCreateNewBookingForStoredCustomer(){
        system.createNewBookingForCustomer("Hans Meier", culturalEvent,2);
    }

    @Test
    void shouldRejectCreationNewBookingForUnstoredCustomer() {
        assertThrows(NoSuchElementException.class, () -> system.createNewBookingForCustomer("Hans Müller", culturalEvent, 2));
    }

    @Test
    void shouldMergeBookingForSameCulturalEventAndCustomer(){
        system.createNewCulturalEvent("Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
        system.createNewBookingForCustomer("Hans Meier",culturalEvent,2);
        system.createNewBookingForCustomer("Hans Meier", culturalEvent, 2);
        Booking storedBooking = system.getBooking("Hans Meier", culturalEvent);
        assertEquals(4, storedBooking.getBookedSeats());
    }

    @Test
    void shouldReturnBookingForCustomerAndCulturalEvent(){
        Booking booking = new Booking(customer,culturalEvent,2);
        system.createNewBookingForCustomer("Hans Meier",culturalEvent,2);
        assertEquals(booking, system.getBooking("Hans Meier",culturalEvent));
    }

    @Test
    void shouldRejectBookingIfNotEnoughSeatsAvailable(){
        system.createNewBookingForCustomer("Hans Meier",culturalEvent,19_000);
        assertThrows(IllegalArgumentException.class, () -> system.createNewBookingForCustomer("Hans Meier", culturalEvent, 1_001));
    }

}
