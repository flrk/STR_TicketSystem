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

    @BeforeEach
    public void init(){
        system = new TicketSystem();
    }

    @Test
    public void shouldCreateNewTicketSystem(){
        assertNotNull(system);
    }

    @Test
    public void shouldCreateNewCustomer(){
        system.createNewCustomer("Hans Meier", "Zechenweg 7, 30499 Hannover");
    }

    @Test
    public void shouldCreateNewCulturalEvent(){
        system.createNewCulturalEvent("Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
    }

    @Test
    public void shouldListAllCulturalEvents(){
        LinkedList<CulturalEvent> listToCompare = new LinkedList<>();
        listToCompare.add(new CulturalEvent(1,"Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000));
        listToCompare.add(new CulturalEvent(2,"Der Ring der Niebelung", LocalDateTime.of(2018, Month.MARCH, 15, 19, 30), 24, 2_000));

        system.createNewCulturalEvent("Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
        system.createNewCulturalEvent("Der Ring der Niebelung", LocalDateTime.of(2018, Month.MARCH, 15, 19, 30), 24, 2_000);

        List<CulturalEvent> culturalEventList = system.listAllCulturalEvents();

        assertTrue(culturalEventList.equals(listToCompare));
    }

    @Test
    public void shouldListAllCustomers(){
        LinkedList<Customer> listToCompare = new LinkedList<>();
        listToCompare.add(new Customer("Hans Meier", "Blabal Str. 4, 99999 Nonsenshausen"));
        listToCompare.add(new Customer("Hans Müller", "TickTack Str. 4, 99999 Nonsenshausen"));

        system.createNewCustomer("Hans Meier", "Blabal Str. 4, 99999 Nonsenshausen");
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
        system.createNewCustomer("Hans Meier", "Blalalalla");
        system.createNewBookingForCustomer("Hans Meier", 1,2);
    }

    @Test
    void shouldRejectCreationNewBookingForUnstoredCustomer() {
        system.createNewCustomer("Hans Meier", "Blalalalla");
        assertThrows(NoSuchElementException.class, () -> system.createNewBookingForCustomer("Hans Müller", 1, 2));
    }

}
