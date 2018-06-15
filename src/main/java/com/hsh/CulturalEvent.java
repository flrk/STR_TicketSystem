package com.hsh;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


public class CulturalEvent implements Serializable {
    private int id;
    private String title;
    private LocalDateTime  datetime;
    private double price;
    private int numberOfSeats;
    private int remainingSeats;
    private String email;


    public CulturalEvent(int id, String title, LocalDateTime dateTime, double price, int numberOfSeats, String email){
        this.id = id;
        this.title = title;
        this.datetime = dateTime;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.remainingSeats = numberOfSeats;
        this.email = email;
    }

    int getEventID(){
        return id;
    }

    void decreaseRemainingSeats(int numberOfSeats){
        remainingSeats -= numberOfSeats;
    }

    int getRemainingSeats(){
        return remainingSeats;
    }

    String getPromotersEmail(){
        return email;
    }

    int getPercentOfBookedSeats(int bookedSeats) { return bookedSeats / (numberOfSeats/100);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CulturalEvent that = (CulturalEvent) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                numberOfSeats == that.numberOfSeats &&
                Objects.equals(title, that.title) &&
                Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, datetime, price, numberOfSeats);
    }
}
