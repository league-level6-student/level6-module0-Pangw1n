package _10_white_box_testing;

import _09_intro_to_white_box_testing.models.DeliveryService;
import _09_intro_to_white_box_testing.models.Order;
import _10_white_box_testing.models.BakeryService;
import _10_white_box_testing.models.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MyDonutShopTest {

    MyDonutShop myDonutShop;
    @Mock
    PaymentService payment;
    @Mock
    DeliveryService delivery;
    @Mock
    BakeryService bakery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    	myDonutShop = new MyDonutShop(payment, delivery, bakery);
    	myDonutShop.openForTheDay();
    }

    @Test
    void itShouldTakeDeliveryOrder() throws Exception {
        //given
        Order order = new Order("CUSTOMER_NAME",
                "CUSTOMER_PHONE_NUMBER",
                1,
                5.00,
                "CREDIT_CARD_NUMBER",
                true);
        //when
        when(bakery.getDonutsRemaining()).thenReturn(1);
        when(payment.charge(order)).thenReturn(true);
    	myDonutShop.takeOrder(order);
        //then
    	verify(delivery, times(1)).scheduleDelivery(order);
    }

    @Test
    void givenInsufficientDonutsRemaining_whenTakeOrder_thenThrowIllegalArgumentException() {
        //given
        Order order = new Order("CUSTOMER_NAME",
                "CUSTOMER_PHONE_NUMBER",
                1,
                5.00,
                "CREDIT_CARD_NUMBER",
                true);
        //when
        when(bakery.getDonutsRemaining()).thenReturn(0);
        Throwable exceptionThrown = assertThrows(Exception.class, () -> myDonutShop.takeOrder(order));
        //then
        assertEquals(exceptionThrown.getMessage(), "Insufficient donuts remaining");
    }

    @Test
    void givenNotOpenForBusiness_whenTakeOrder_thenThrowIllegalStateException(){
        //given
        Order order = new Order("CUSTOMER_NAME",
                "CUSTOMER_PHONE_NUMBER",
                1,
                5.00,
                "CREDIT_CARD_NUMBER",
                true);
        myDonutShop.closeForTheDay();
        //when
        Throwable exceptionThrown = assertThrows(Exception.class, () -> myDonutShop.takeOrder(order));
        //then
        assertEquals(exceptionThrown.getMessage(), "Sorry we're currently closed");
        myDonutShop.openForTheDay();
    }

}