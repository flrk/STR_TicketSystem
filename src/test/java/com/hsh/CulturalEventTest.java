package com.hsh;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class CulturalEventTest {
    private int seats;
    private CulturalEvent event;

    @BeforeEach
    void init(){
        seats = 20_000;
        event = new CulturalEvent(1,"Metallica Konzert",LocalDateTime.of(2018, Month.MARCH, 28, 19, 30),98, seats);
    }

    @Test
    void shouldCreateFullCulturalEvent(){
        assertNotNull(event);
    }

    @Test
    void shouldGetCulturalEventId(){
        assertEquals(1,event.getEventID());
    }

    @Test
    void shouldShowRemainingSeats(){
        int remainingSeats = event.getRemainingSeats();
        assertEquals(seats,remainingSeats);
    }

    @Test
    void shouldDecreaseRemainingSeats(){
        event.decreaseRemainingSeats(2);
        assertEquals(19_998, event.getRemainingSeats());
    }


}
