package be.heydari.ast;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class NumberTermValueTest {
    private static Logger logger = Logger.getLogger(NumberTermValueTest.class.getName());


    @Test
    public void isNumber() {
        String value = "13l";
        assertTrue(NumberTermValue.isNumber(value));

        value = "13L";
        assertTrue(NumberTermValue.isNumber(value));

        value = "13i";
        assertTrue(NumberTermValue.isNumber(value));

        value = "13I";
        assertTrue(NumberTermValue.isNumber(value));

        value = "13d";
        assertTrue(NumberTermValue.isNumber(value));

        value = "13D";
        assertTrue(NumberTermValue.isNumber(value));

        value = "13f";
        assertTrue(NumberTermValue.isNumber(value));

        value = "13F";
        assertTrue(NumberTermValue.isNumber(value));

        value = "13";
        assertFalse(NumberTermValue.isNumber(value));

        value = "l";
        assertFalse(NumberTermValue.isNumber(value));

        value = "13ll";
        assertFalse(NumberTermValue.isNumber(value));
    }

    @Test
    public void testTypeFromConstant() {
        String value = "13l";
        assertTrue(NumberTermValue.isNumber(value));
        assertEquals(NumberType.LONG, NumberTermValue.typeFromConstant(value));

        value = "13L";
        assertTrue(NumberTermValue.isNumber(value));
        assertEquals(NumberType.LONG, NumberTermValue.typeFromConstant(value));

        value = "13i";
        assertTrue(NumberTermValue.isNumber(value));
        assertEquals(NumberType.INT, NumberTermValue.typeFromConstant(value));

        value = "13I";
        assertTrue(NumberTermValue.isNumber(value));
        assertEquals(NumberType.INT, NumberTermValue.typeFromConstant(value));

        value = "13d";
        assertTrue(NumberTermValue.isNumber(value));
        assertEquals(NumberType.DOUBLE, NumberTermValue.typeFromConstant(value));

        value = "13D";
        assertTrue(NumberTermValue.isNumber(value));
        assertEquals(NumberType.DOUBLE, NumberTermValue.typeFromConstant(value));

        value = "13f";
        assertTrue(NumberTermValue.isNumber(value));
        assertEquals(NumberType.FLOAT, NumberTermValue.typeFromConstant(value));

        value = "13F";
        assertTrue(NumberTermValue.isNumber(value));
        assertEquals(NumberType.FLOAT, NumberTermValue.typeFromConstant(value));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testTypeOnlyNumber() {
        String value = "13";
        assertFalse(NumberTermValue.isNumber(value));
        NumberTermValue.typeFromConstant(value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeWrongNumber() {
        String value = "13p";
        assertFalse(NumberTermValue.isNumber(value));
        NumberTermValue.typeFromConstant(value);
    }


}