package com.queomedia.commons.checks;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import junit.framework.Assert;

public class CheckTest {

    /**
     * Test method for {@link com.queomedia.util.asserts.Check#argumentInstanceOf(java.lang.Object, java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testArgumentInstanceOf_normal() {

        Object value = new BigInteger("1");
        Assert.assertSame(value, Check.argumentInstanceOf(value, Number.class, "test1"));

        Assert.assertSame(value, Check.argumentInstanceOf(value, BigInteger.class, "test2"));
    }

    /**
     * Test method for {@link com.queomedia.util.asserts.Check#argumentInstanceOf(java.lang.Object, java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testArgumentInstanceOf_normalFail() {

        Object value = new Object();
        try {
            Check.argumentInstanceOf(value, Collection.class, "test1");
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            //Excpected
        }
    }

    /**
     * Test method for {@link com.queomedia.util.asserts.Check#argumentInstanceOf(java.lang.Object, java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testArgumentInstanceOf_generics() {
        Object value = new ArrayList<BigInteger>(0);
        Assert.assertSame(value, Check.argumentInstanceOf(value, ArrayList.class, "test1"));
    }

    @Test
    public void testArgumentGreaterEquals() {
        Check.argumentGreaterEquals(0, 3, "test");
        /* ok */
    }

    @Test
    public void testArgumentGreaterEquals_faius() {
        try {
            Check.argumentGreaterEquals(3, 0, "test");
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    @Test
    public void testArgumentGreaterEquals_NaN() {
        try {
            Check.argumentGreaterEquals(0, Double.NaN, "test");
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    @Test
    public void testArgumentGreaterEquals_PositiveInfinitiy() {
        Check.argumentGreaterEquals(0, Double.POSITIVE_INFINITY, "test");

    }

    @Test
    public void testArgumentGreaterEquals_NegativeInfinitiy() {
        try {
            Check.argumentGreaterEquals(0, Double.NEGATIVE_INFINITY, "test");
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    @Test
    public void testEqualsArgument() {
        Check.equalsArgument("s" + "1", "s1", "arg");
    }

    @Test
    public void testEqualsArgument_fail() {
        try {
            Check.equalsArgument("s", "s1", "arg");
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    @Test
    public void testEqualsArgument_fail_null() {
        try {
            Check.equalsArgument("s", null, "arg");
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    @Test
    public void testEqualsOrNullArgument() {
        Check.equalsOrNullArgument("s" + "1", "s1", "arg");
    }

    @Test
    public void testEqualsOrNullArgument_null() {
        Check.equalsOrNullArgument("s", null, "arg");
    }

    @Test
    public void testEqualsOrNullArgument_fail() {
        try {
            Check.equalsOrNullArgument("s", "s1", "arg");
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    @Test
    public void testNotNegativeArgument() {        
        Check.notNegativeArgument(1, "arg");           
    }
    
    @Test
    public void testNotNegativeArgument_zero() {        
        Check.notNegativeArgument(0, "arg");             
    }
    
    @Test
    public void testNotNegativeArgument_fail() {
        try {
            Check.notNegativeArgument(-1, "arg");
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }
}
