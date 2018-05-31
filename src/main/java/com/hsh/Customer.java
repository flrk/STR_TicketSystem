package com.hsh;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    private String name;
    private String address;

    public Customer(String name, String address){
        setName(name);
        setAddress(address);
    }

    public String getName(){
        return name;
    }

    private void setName(String name){
        if(name == null || name.equals("")){
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    private void setAddress(String address){
        if(address == null || address.equals("")){
            throw new IllegalArgumentException();
        }
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
                Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, address);
    }
}
