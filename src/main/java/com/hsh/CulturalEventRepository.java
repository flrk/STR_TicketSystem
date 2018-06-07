package com.hsh;

import java.io.*;
import java.util.Collection;
import java.util.List;


public class CulturalEventRepository {

    public List<CulturalEvent> findAllCulturalEvents() throws IOException {
        List<CulturalEvent> tmp;

        try(FileInputStream fis = new FileInputStream("persistence/culturalEvents.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            tmp = (List<CulturalEvent>) ois.readObject();
        }catch(Exception e){
            throw new IOException("Could not load data");
        }
        return tmp;
    }

    public void saveAllCulturalEvents(Collection<CulturalEvent> culturalEvents) throws IOException {
        try(FileOutputStream fos = new FileOutputStream("persistence/culturalEvents.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos) ){
            oos.writeObject(culturalEvents);
        }catch(IOException ioe){
            throw new IOException("Could not save data");
        }
    }
}
