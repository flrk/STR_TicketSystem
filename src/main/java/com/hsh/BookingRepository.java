package com.hsh;


import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BookingRepository {

    public List<Booking> findAllBookings() throws IOException {
        List<Booking> tmp = new LinkedList<>();

        try(FileInputStream fis = new FileInputStream("persistence/bookings.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            tmp = (List<Booking>) ois.readObject();
        }catch(Exception e){
            throw new IOException("Could not load data");
        }
        return tmp;
    }

    public void saveAllBookings(Collection<Booking> bookings) throws IOException {
        try(FileOutputStream fos = new FileOutputStream("persistence/bookings.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos) ){
            oos.writeObject(bookings);
        }catch(IOException ioe){
            throw new IOException("Could not save Data");
        }
    }
}
