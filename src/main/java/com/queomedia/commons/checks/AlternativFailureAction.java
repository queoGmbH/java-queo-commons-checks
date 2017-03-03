/**
 * 
 */
package com.queomedia.commons.checks;

/**
 * Define what to do if a failure should not result in its default behavior.
 * @author Engelmann
 *
 */
@Deprecated
public enum AlternativFailureAction {

    /** Do noting. */
    NONE,

    /** Write a log statement. */
    LOG,

    /** Write a statement to system.out */
    SYSTEM_OUT;

}
