package com.queomedia.commons.checks;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.queomedia.commons.exceptions.ConstraintViolationException;

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
    @Test(expected = IllegalArgumentException.class)
    public void testArgumentInstanceOf_normalFail() {
        Check.argumentInstanceOf(new Object(), Collection.class, "test1");
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

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentGreaterEquals_faius() {
        Check.argumentGreaterEquals(3, 0, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentGreaterEquals_NaN() {

        Check.argumentGreaterEquals(0, Double.NaN, "test");

    }

    @Test
    public void testArgumentGreaterEquals_PositiveInfinitiy() {
        Check.argumentGreaterEquals(0, Double.POSITIVE_INFINITY, "test");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentGreaterEquals_NegativeInfinitiy() {

        Check.argumentGreaterEquals(0, Double.NEGATIVE_INFINITY, "test");

    }

    @Test
    public void testEqualsArgument() {
        Check.equalsArgument("s" + "1", "s1", "arg");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqualsArgument_fail() {

        Check.equalsArgument("s", "s1", "arg");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqualsArgument_fail_null() {

        Check.equalsArgument("s", null, "arg");

    }

    @Test
    public void testEqualsOrNullArgument() {
        Check.equalsOrNullArgument("s" + "1", "s1", "arg");
    }

    @Test
    public void testEqualsOrNullArgument_null() {
        Check.equalsOrNullArgument("s", null, "arg");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqualsOrNullArgument_fail() {
        Check.equalsOrNullArgument("s", "s1", "arg");

    }

    @Test
    public void testNotNegativeArgument() {
        Check.notNegativeArgument(1, "arg");
    }

    @Test
    public void testNotNegativeArgument_zero() {
        Check.notNegativeArgument(0, "arg");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotNegativeArgument_fail() {
        Check.notNegativeArgument(-1, "arg");
    }

    @Test
    public void testUniqueElementsArgument() {
        Check.uniqueElementsArgument(Arrays.asList(1, 2, 3, 4), IntegerEqualsChecker.INSTANCHE, "argumentName");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUniqueElementsArgument_fail() {
        Check.uniqueElementsArgument(Arrays.asList(1, 2, 3, 1), IntegerEqualsChecker.INSTANCHE, "argumentName");
    }
    
    @Test
    public void testUniqueElementsArgument_NativeEquals() {
        Check.uniqueElementsArgument(Arrays.asList(1, 2, 3, 4), "argumentName");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUniqueElementsArgument_NativeEquals_fail() {
        Check.uniqueElementsArgument(Arrays.asList(1, 2, 3, 1), "argumentName");
    }
    
    
    @Test
    public void testUniqueElements() {
        Check.uniqueElements(Arrays.asList(1, 2, 3, 4), IntegerEqualsChecker.INSTANCHE);
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void testUniqueElements_fail() {
        Check.uniqueElements(Arrays.asList(1, 2, 3, 1), IntegerEqualsChecker.INSTANCHE);
    }
    
    @Test
    public void testUniqueElements_NativeEquals() {
        Check.uniqueElements(Arrays.asList(1, 2, 3, 4));
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void testUniqueElements_fail_NativeEquals() {
        Check.uniqueElements(Arrays.asList(1, 2, 3, 1));
    }
}
