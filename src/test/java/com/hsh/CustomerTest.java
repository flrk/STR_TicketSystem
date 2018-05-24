package com.hsh;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

   @Test
    void shouldCreateFullCustomer(){
        Customer customer = new Customer("Hans Meier", "Zechenweg 7, 20146 Hamburg");
        assertNotNull(customer);
    }

    @Test
    void shouldRejectCreatingCustomerWithoutName(){
        assertThrows(IllegalArgumentException.class, () -> new Customer("","Zechenweg 7, 20146 Hamburg"));
    }

    @Test
    void shouldRejectCreatingCustomerWithoutAddress(){
        assertThrows(IllegalArgumentException.class, () -> new Customer("Hans Meier", null));
    }
}
