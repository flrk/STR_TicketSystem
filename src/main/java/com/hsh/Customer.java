package com.hsh;

import java.util.Objects;

public class Customer {
    private String name;
    private String adress;

    public Customer(String name, String address){
        setName(name);
        setAddress(address);
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
        this.adress = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
                Objects.equals(adress, customer.adress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, adress);
    }
}
