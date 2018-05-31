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


    public CulturalEvent(int id, String title, LocalDateTime dateTime, double price, int numberOfSeats){
        this.id = id;
        this.title = title;
        this.datetime = dateTime;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.remainingSeats = numberOfSeats;
    }

    public int getEventID(){
        return id;
    }

    public void decreaseRemainingSeats(int numberOfSeats){
        remainingSeats -= numberOfSeats;
    }

    public int getRemainingSeats(){
        return remainingSeats;
    }

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

    @Override
    public String toString(){
        return "ID "+id+" : "+title+" am "+datetime.toLocalDate()+" um "+datetime.toLocalTime();
    }
}
