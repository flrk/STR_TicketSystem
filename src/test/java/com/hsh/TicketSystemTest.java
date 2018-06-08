package com.hsh;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TicketSystemTest {
    private Customer customerOnBlacklist;
    private TicketSystem system;
    private Customer customer;
    private CulturalEvent culturalEvent;
    private String customerName;
    private int bookedSeats;

    @BeforeEach
    void init(){

        customer = new Customer("Hans Meier", "Zechenweg 7, 30499 Hannover");
        customerOnBlacklist = new Customer("Evil Twin", "Blacklist 7, 66666 Blacklisthausen");
        culturalEvent = new CulturalEvent(1,"Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
        customerName = customer.getName();
        bookedSeats = 2;

        //Inject BlacklistService as MockObjekt into the Ticketservice
        BlacklistService bs = Mockito.mock(BlacklistService.class);
        Mockito.when(bs.isCustomerOnBlacklist(customerOnBlacklist)).thenReturn(true);
        system = new TicketSystem(bs);

        //Create some example Customers
        system.createNewCustomer("Evil Twin", "Blacklist 7, 66666 Blacklisthausen");
        system.createNewCustomer("Hans Meier", "Zechenweg 7, 30499 Hannover");
        system.createNewCustomer("Hans Mayer", "Blabal Str. 4, 99999 Nonsenshausen");
        system.createNewCustomer("Hans Müller", "TickTack Str. 4, 99999 Nonsenshausen");

        //Create example CulturalEvents
        system.createNewCulturalEvent("Metallica Konzert", LocalDateTime.of(2018, Month.MARCH, 28, 19, 30), 98.54, 20_000);
        system.createNewCulturalEvent("Der Ring der Niebelung", LocalDateTime.of(2018, Month.MARCH, 15, 19, 30), 24, 2_000);
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

        List<CulturalEvent> culturalEventList = system.listAllCulturalEvents();

        assertTrue(culturalEventList.equals(listToCompare));
    }

    @Test
    void shouldListAllCustomers(){
        LinkedList<Customer> listToCompare = new LinkedList<>();
        listToCompare.add(new Customer("Evil Twin", "Blacklist 7, 66666 Blacklisthausen"));
        listToCompare.add(new Customer("Hans Meier", "Zechenweg 7, 30499 Hannover"));
        listToCompare.add(new Customer("Hans Mayer", "Blabal Str. 4, 99999 Nonsenshausen"));
        listToCompare.add(new Customer("Hans Müller", "TickTack Str. 4, 99999 Nonsenshausen"));

        List<Customer> customerList = system.listAllCustomers();

        assertTrue(customerList.equals(listToCompare));

    }

    @Test
    void shouldListRemainingSeatsForGivenCulturalEvent(){
        int remainingSeats = system.getRemainingSeatsForCulturalEvent(1);
        assertEquals(20_000, remainingSeats);
    }

    @Test
    void shouldCreateNewBookingForStoredCustomer(){
        try {
            system.createNewBookingForCustomer(customerName, culturalEvent, bookedSeats);
        } catch (CustomerRejectedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldRejectCreationNewBookingForUnstoredCustomer() {
        assertThrows(NoSuchElementException.class, () -> system.createNewBookingForCustomer("Max Mustermann", culturalEvent, bookedSeats));
    }

    @Test
    void shouldMergeBookingForSameCulturalEventAndCustomer(){
        int expectedSeats = bookedSeats + bookedSeats;

        try {
            system.createNewBookingForCustomer(customerName, culturalEvent, bookedSeats);
            system.createNewBookingForCustomer(customerName, culturalEvent, bookedSeats);
        } catch (CustomerRejectedException e) {
            e.printStackTrace();
        }

        Booking storedBooking = system.getBooking(customerName, culturalEvent);
        assertEquals(expectedSeats, storedBooking.getBookedSeats());
    }

    @Test
    void shouldReturnBookingForCustomerAndCulturalEvent(){
        Booking expectedBooking = new Booking(customer, culturalEvent, bookedSeats);
        try {
            system.createNewBookingForCustomer(customerName, culturalEvent, bookedSeats);
        } catch (CustomerRejectedException e) {
            e.printStackTrace();
        }
        assertEquals(expectedBooking, system.getBooking(customerName, culturalEvent));
    }

    @Test
    void shouldRejectBookingIfNotEnoughSeatsAvailable(){
        int firstBookedSeats = 19_000;
        int secondBookedSeats = 1_001;
        try {
            system.createNewBookingForCustomer(customerName, culturalEvent, firstBookedSeats);
        } catch (CustomerRejectedException e) {
            e.printStackTrace();
        }
        assertThrows(IllegalArgumentException.class, () -> system.createNewBookingForCustomer(customerName, culturalEvent, secondBookedSeats));
    }

    @Test
    void shouldPersistAllContent(){
        assertTrue(system.saveAllData());
    }

    @Test
    void shouldRejectBookingIfCustomerIsOnBlacklist(){
        assertThrows(CustomerRejectedException.class, () -> system.createNewBookingForCustomer(customerOnBlacklist.getName(), culturalEvent, bookedSeats));
    }

    @Test
    void shouldLoadAllSavedData(){
        try {
            system.createNewBookingForCustomer(customerName, culturalEvent, bookedSeats);
        } catch (CustomerRejectedException e) {
            e.printStackTrace();
        }
        List<CulturalEvent> culturalEvents = system.listAllCulturalEvents();
        List<Customer> customers = system.listAllCustomers();
        List<Booking> bookings = new LinkedList<>();
        for (Customer c: customers) {
            for (CulturalEvent ce: culturalEvents) {
                Booking b = system.getBooking(c.getName(),ce);
                if (b != null && !bookings.contains(b)){
                    bookings.add(b);

                }
            }
        }

        system.saveAllData();
        system.eraseTemporaryData();
        system.loadAllData();

        List<CulturalEvent> newCulturalEvents = system.listAllCulturalEvents();
        List<Customer> newCustomers = system.listAllCustomers();

        List<Booking> newBookings = new LinkedList<>();
        for (Customer c: newCustomers) {
            for (CulturalEvent ce: newCulturalEvents) {
                Booking b = system.getBooking(c.getName(),ce);
                if (b != null) {
                    newBookings.add(b);
                }
            }
        }

        assertEquals(culturalEvents, newCulturalEvents);
        assertEquals(customers, newCustomers);
        assertEquals(bookings, newBookings);
    }

}
