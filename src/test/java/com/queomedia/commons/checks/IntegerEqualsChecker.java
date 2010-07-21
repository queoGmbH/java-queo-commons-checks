package com.queomedia.commons.checks;

import com.queomedia.commons.equals.EqualsChecker;

/**
 * Check if two integers are equals. 
 * @author Ralph Engelmann
 *
 */
public final class IntegerEqualsChecker implements EqualsChecker<Integer, Integer> {

    /** The only one instance */
    public static final IntegerEqualsChecker INSTANCHE = new IntegerEqualsChecker();
    
    /** Use {@link #INSTANCHE} instead. */
    private IntegerEqualsChecker() {
        super();
    }
    
    @Override
    public boolean equals(Integer objectT, Integer objectK) {
        if (objectT == null) {
            return objectK == null;
        } else {
            return objectT.equals(objectK);
        }
    }

}
