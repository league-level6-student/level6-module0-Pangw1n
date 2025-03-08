package _08_mocking.models;

import _07_intro_to_mocking.models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DeliveryDriverTest {

    DeliveryDriver deliveryDriver;
    
    @Mock
    CellPhone cellPhone;
    @Mock
    Car car;

    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	deliveryDriver = new DeliveryDriver("Driver", car, cellPhone);
    }

    @Test
    void itShouldWasteTime() {
        //given
    	when(cellPhone.browseCatMemes()).thenReturn(true);

        //when
    	boolean actual = deliveryDriver.wasteTime();
        //then
    	assertEquals(true, actual);
    }

    @Test
    void itShouldRefuel() {
        //given
    	when(car.fillTank(101)).thenReturn(true);
        //when
    	boolean actual = deliveryDriver.refuel(101);
        //then
    	assertTrue(actual);
    }

    @Test
    void itShouldContactCustomer() {
        //given
    	String phoneNumber = "555-000-0000";
    	when(cellPhone.call(phoneNumber)).thenReturn(true);
        //when
    	boolean result = deliveryDriver.contactCustomer(phoneNumber);
        //then
    }

}