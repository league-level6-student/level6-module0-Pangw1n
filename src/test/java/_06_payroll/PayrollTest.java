package _06_payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayrollTest {

    Payroll payroll = new Payroll();

    @Test
    void itShouldCalculatePaycheck() {
        //given
        double a = 15;
        int b = 40;
        double expected = 600;

        //when
        double actual = payroll.calculatePaycheck(a, b);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void itShouldCalculateMileageReimbursement() {
        //given
        int a = 1000;
        double expected = 575;

        //when
        double actual = payroll.calculateMileageReimbursement(a);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void itShouldCreateOfferLetter() {
        //given
        String a = "Bob";
        double b = 20;
        String expected = "Hello Bob, We are pleased to offer you an hourly wage of 20.0";

        //when
        String actual = payroll.createOfferLetter(a, b);

        //then
        assertEquals(expected, actual);
    }

}