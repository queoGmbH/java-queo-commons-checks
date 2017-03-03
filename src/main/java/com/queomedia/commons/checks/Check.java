package com.queomedia.commons.checks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.queomedia.commons.equals.EqualsChecker;
import com.queomedia.commons.equals.NativeEqualsChecker;
import com.queomedia.commons.exceptions.ArgumentNullException;
import com.queomedia.commons.exceptions.ConstraintViolationException;
import com.queomedia.commons.exceptions.NotImplementedCaseExecption;

/**
 * This tool class provides several checks. This class is designed for use in
 * normal classes (not test cases).
 * 
 * All argument checks throw an {@code IllegalArgumentException} or a subclass
 * if the check fails. All other checks throw an {@code
 * ConstraintViolationException} if the check fails.
 */
public final class Check {

    /**
     * Define the alternative action when the normal check fail action is deactivated.
     */
    @Deprecated
    private static AlternativFailureAction alternativeFailureAction = AlternativFailureAction.NONE;

    /**
     * Activate the check. If this value is true, then the check do its default
     * routine when the check fails (for example an exception). If this value is
     * false, then {@code alternativeFailureAction} define the action for a
     * failure.
     */
    @Deprecated
    private static boolean activeArgumentCheck = true;

    /**
     * Util classes need no constructor.
     */
    private Check() {
        super();
    }

    /**
     * Gets the alternative failure action. Define the alternative action when
     * the normal check fail action is deactivated.
     * 
     * @return the alternative failure action
     */
    @Deprecated
    public static AlternativFailureAction getAlternativeFailureAction() {
        return Check.alternativeFailureAction;
    }

    /**
     * Sets the alternative failure action. Define the alternative action when
     * the normal check fail action is deactivated.
     * 
     * @param alternativeFailureAction
     *            the new alternative failure action
     */
    @Deprecated
    public static void setAlternativeFailureAction(final AlternativFailureAction alternativeFailureAction) {
        Check.alternativeFailureAction = alternativeFailureAction;
    }

    /**
     * Gets the active argument check. If this value is true, then the check do
     * its default routine when the check fails (for example an exception). If
     * this value is false, then {@code alternativeFailureAction} define the
     * action for a failure.
     * 
     * @return the active argument check
     */
    @Deprecated
    public static boolean getActiveArgumentCheck() {
        return Check.activeArgumentCheck;
    }

    /**
     * Sets the active argument check. If this value is true, then the check do
     * its default routine when the check fails (for example an exception). If
     * this value is false, then {@code alternativeFailureAction} define the
     * action for a failure.
     * 
     * @param activeArgumentCheck
     *            the new active argument check
     */
    @Deprecated
    public static void setActiveArgumentCheck(final boolean activeArgumentCheck) {
        Check.activeArgumentCheck = activeArgumentCheck;
    }

    /**
     * Alternitive failure action.
     * 
     * @param message
     *            the message
     */
    @Deprecated
    private static void alternativeFailureAction(final String message) {
        switch (Check.alternativeFailureAction) {

        case NONE:
            return;
        case LOG:
            System.out.println("Check failure - message=" + message);
            return;
        case SYSTEM_OUT:
            System.out.println("Check failure - message=" + message);
            return;
        default:
            throw new NotImplementedCaseExecption(
                    "alternativeFailureAction " + Check.alternativeFailureAction + " is not implemented");
        }
    }

    /**
     * Alternitive failure action.
     * 
     * @param e
     *            the e
     */
    @Deprecated
    private static void alternativeFailureAction(final Exception e) {
        Check.alternativeFailureAction(e.toString());
    }

    /**
     * Checks for (not) null argument.
     * 
     * @param argument
     *            the argument
     * @param argumentName
     *            the argument name
     * 
     * @throws ArgumentNullException
     *             if the argument is null
     */
    public static void notNullArgument(final Object argument, final String argumentName) throws ArgumentNullException {
        if (argument == null) {
            ArgumentNullException argNullException = new ArgumentNullException(argumentName);
            if (Check.activeArgumentCheck) {
                throw argNullException;
            } else {
                Check.alternativeFailureAction(argNullException);
            }
        }
    }

    /**
     * Checks for (not) empty String argument.
     *
     * @param argument            the argument
     * @param argumentName            the argument name
     * @throws IllegalArgumentException             if the argument is empty
     * @throws ArgumentNullException             if the argument is null
     */
    public static void notEmptyArgument(final String argument, final String argumentName)
            throws IllegalArgumentException {
        Check.notNullArgument(argument, argumentName);
        Check.notNullArgument(argumentName, "argumentName");

        if (argument.length() == 0) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - String argument " + argumentName + " must have length");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Checks for (not) empty Collection argument.
     *
     * @param argument            the argument
     * @param argumentName            the argument name
     * @throws IllegalArgumentException             if the argument is empty
     * @throws ArgumentNullException             if the argument is null
     */
    public static void notEmptyArgument(final Collection<?> argument, final String argumentName)
            throws IllegalArgumentException {
        Check.notNullArgument(argument, argumentName);
        Check.notNullArgument(argumentName, "argumentName");

        if (argument.isEmpty()) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - String argument " + argumentName + " must have length");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Argument instance of.
     *
     * @param <T> the generic type
     * @param argument            the argument
     * @param type            the type
     * @param argumentName            the argument name
     * @return the T
     */
    @SuppressWarnings("unchecked")
    public static <T> T argumentInstanceOf(final Object argument, final Class<T> type, final String argumentName) {
        Check.notNullArgument(argument, argumentName);
        Check.notNullArgument(argumentName, "argumentName");

        if (!type.isAssignableFrom(argument.getClass())) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - type " + type.getName() + " expected for argument " + argumentName
                            + " but get an object of type " + argument.getClass().getName());
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
        return (T) argument;
    }

    /**
     * Checks that an argument collection does not contain an null element.
     * 
     * @param argument
     *            the argument itself
     * @param argumentName
     *            the name of the argument
     */
    public static void notNullElementArgument(final Collection<?> argument, final String argumentName) {
        Check.notNullArgument(argument, argumentName);
        Check.notNullArgument(argumentName, "argumentName");

        if (argument.contains(null)) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException("[Assertion failed] - collection "
                    + argumentName + " should not contain a null element, but it has one or more - " + argumentName
                    + " = " + argument);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * False argument.
     * 
     * @param bool
     *            the bool
     * @param argumentName
     *            the argument name
     */
    public static void falseArgument(final boolean bool, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (bool) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - boolean argument " + argumentName + " should be false but is true");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Not zero.
     * 
     * @param value
     *            the value
     * @param argumentName
     *            the argument name
     */
    public static void notZeroArgument(final int value, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (value == 0) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the int argument " + argumentName + " must not be zero");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Not zero.
     * 
     * @param value
     *            the value
     * @param argumentName
     *            the argument name
     */
    public static void notZeroArgument(final long value, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (value == 0) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the int argument " + argumentName + " must not be zero");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Not zero or negative.
     * 
     * @param value
     *            the value
     * @param argumentName
     *            the argument name
     */
    public static void notZeroOrNegativeArgument(final int value, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (value <= 0) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the int argument " + argumentName + " must not be zero or negative");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Not zero or negative.
     * 
     * @param value
     *            the value
     * @param argumentName
     *            the argument name
     */
    public static void notZeroOrNegativeArgument(final long value, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (value <= 0) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the int argument " + argumentName + " must not be zero or negative");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Not zero argument.
     *
     * @param value the value
     * @param epsylon the epsylon
     * @param argumentName the argument name
     */
    public static void notZeroArgument(final double value, final double epsylon, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (Math.abs(value) < epsylon) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the int argument " + argumentName + " must not be zero");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Not zero or negative.
     * 
     * @param value
     *            the value
     * @param argumentName
     *            the argument name
     */
    public static void notNegativeArgument(final int value, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (value < 0) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the int argument " + argumentName + " must not negative");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Not zero or negative.
     * 
     * @param value
     *            the value
     * @param argumentName
     *            the argument name
     */
    public static void notNegativeArgument(final long value, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (value < 0) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the int argument " + argumentName + " must not negative");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Not zero or negative.
     * 
     * @param value
     *            the value
     * @param argumentName
     *            the argument name
     */
    public static void notNegativeArgument(final double value, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");
        if (value < 0) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the int argument " + argumentName + " must not negative");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Checks if is equals.
     *
     * @param valueA            value of argument b
     * @param valueB            value of argument a
     * @param argumentNameA            the name of argument a
     * @param argumentNameB            the name of argument b
     */
    public static void equalArguments(final Object valueA, final Object valueB, final String argumentNameA,
            final String argumentNameB) {
        if (valueA == null) {
            if (valueB == null) {
                return;
            } else {
                IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                        "[Assertion failed] - the arguments " + argumentNameA + " and " + argumentNameB
                                + " are not equal, the firstone is " + valueA + " the second one is " + valueB);
                if (Check.activeArgumentCheck) {
                    throw illegalArgExc;
                } else {
                    Check.alternativeFailureAction(illegalArgExc);
                }
            }
        } else {
            if (!valueA.equals(valueB)) {
                IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                        "[Assertion failed] - the arguments " + argumentNameA + " and " + argumentNameB
                                + " are not equal, the firstone is " + valueA + " the second one is " + valueB);
                if (Check.activeArgumentCheck) {
                    throw illegalArgExc;
                } else {
                    Check.alternativeFailureAction(illegalArgExc);
                }
            }
        }
    }

    /**
     * Checks if is equals.
     *
     * @param valueA            value of argument b
     * @param valueB            value of argument a
     * @param argumentNameA            the name of argument a
     * @param argumentNameB            the name of argument b
     */
    public static void equalArguments(final int valueA, final int valueB, final String argumentNameA,
            final String argumentNameB) {

        if (valueA != valueB) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - the arguments " + argumentNameA + " and " + argumentNameB
                            + " are not equal, the firstone is " + valueA + " the second one is " + valueB);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }

    }

    /**
     * Checks that the value of an integer is between a minimal or a maximal
     * value (min &lt;= value &lt;= max).
     * 
     * @param value
     *            the value to check
     * @param min
     *            the minimal allowed value
     * @param max
     *            the maximal allowed value
     * @param argumentName
     *            the name of the argument.
     */
    public static void argumentBetween(final int value, final int min, final int max, final String argumentName) {
        if (value < min) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - this String argument <code>" + argumentName //$NON-NLS-1$
                            + "</code> must be greater or equals " + min + " but is " + value);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
        if (value > max) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - this String argument <code>" + argumentName //$NON-NLS-1$
                            + "</code> must be less or equals " + max + " but is " + value);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Checks that the value of an double is between a minimal or a maximal
     * value.
     * 
     * @param value
     *            the value to check
     * @param min
     *            the minimal allowed value
     * @param max
     *            the maximal allowed value
     * @param argumentName
     *            the name of the argument.
     */
    public static void argumentBetween(final double value, final double min, final double max,
            final String argumentName) {
        if (value < min) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - this String argument <code>" + argumentName //$NON-NLS-1$
                            + "</code> must be greater or equals " + min + " but is " + value);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
        if (value > max) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - this String argument <code>" + argumentName //$NON-NLS-1$
                            + "</code> must be less or equals " + max + " but is " + value);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Checks that the value of an double is greater or equals a minimal value.
     *
     * @param min            the minimal allowed value
     * @param argumentValue            the value to check
     * @param argumentName            the name of the argument.
     */
    public static void argumentGreaterEquals(final int min, final int argumentValue, final String argumentName) {
        /**
         * Do not change this to (argumentValue < min) because when
         * argumentValue is NaN then (NaN < min) is true
         */
        if (!(argumentValue >= min)) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - this String argument <code>" + argumentName //$NON-NLS-1$
                            + "</code> must be greater or equals " + min + " but is " + argumentValue);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Checks that the value of an double is greater or equals a minimal value.
     *
     * @param min            the minimal allowed value
     * @param argumentValue            the value to check
     * @param argumentName            the name of the argument.
     */
    public static void argumentGreaterEquals(final long min, final long argumentValue, final String argumentName) {
        /**
         * Do not change this to (argumentValue < min) because when
         * argumentValue is NaN then (NaN < min) is true
         */
        if (!(argumentValue >= min)) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - this String argument <code>" + argumentName //$NON-NLS-1$
                            + "</code> must be greater or equals " + min + " but is " + argumentValue);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Checks that the value of an double is greater or equals a minimal double
     * value.
     *
     * @param min            the minimal allowed value
     * @param argumentValue            the value to check
     * @param argumentName            the name of the argument.
     */
    public static void argumentGreaterEquals(final double min, final double argumentValue, final String argumentName) {
        /**
         * Do not change this to (argumentValue < min) because when
         * argumentValue is NaN then (NaN < min) is true
         */
        if (!(argumentValue >= min)) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - this String argument <code>" + argumentName //$NON-NLS-1$
                            + "</code> must be greater or equals " + min + " but is " + argumentValue);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Checks that the value of an double is less or equals a maximal value.
     *
     * @param max the max
     * @param argumentValue            the value to check
     * @param argumentName            the name of the argument.
     */
    public static void argumentLessEquals(final int max, final double argumentValue, final String argumentName) {
        /**
         * Do not change this to (argumentValue > max) because when
         * argumentValue is NaN then (NaN > max) is true
         */
        if (!(argumentValue <= max)) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - this String argument <code>" + argumentName //$NON-NLS-1$
                            + "</code> must be less or equals " + max + " but is " + argumentValue);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Assert that exactly {@code numberNotNullExpected} number of parameters and not null.
     * 
     * @param numberNotNullExpected
     *            the number not null expected
     * @param argumentNames
     *            the parameter names
     * @param arguments
     *            the parameters
     */
    public static void argumentExactNotNullCount(final int numberNotNullExpected, final String argumentNames,
            final Object... arguments) {
        int notNullFound = 0;

        int size = arguments.length;
        for (int i = 0; i < size; i++) {
            if (arguments[i] != null) {
                notNullFound++;
            }
        }

        if (numberNotNullExpected != notNullFound) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException("[Assertion failed] - the arguments "
                    + argumentNames + " contains " + notNullFound + " argument which are not null, but expected are "
                    + numberNotNullExpected + " - parameters=" + Arrays.toString(arguments));
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Check the assert that both collections has the same size and are not
     * null.
     * 
     * @param collectionA
     *            the collection a
     * @param collectionB
     *            the collection b
     * @param argumentNameA
     *            the argument name a
     * @param argumentNameB
     *            the argument name b
     */
    public static void sameSizeArgument(final Collection<?> collectionA, final Collection<?> collectionB,
            final String argumentNameA, final String argumentNameB) {
        Check.notNullArgument(collectionA, argumentNameA);
        Check.notNullArgument(collectionB, argumentNameB);
        Check.notNullArgument(argumentNameA, "argumentNameA");
        Check.notNullArgument(argumentNameB, "argumentNameB");

        if (collectionA.size() != collectionB.size()) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - collections have different size: " + argumentNameA + ".size() = " //$NON-NLS-1$
                            + collectionA.size() + " , " + argumentNameB + ".size() = " + collectionB.size()
                            + "\n argumentNameA = " + argumentNameA + ",\n argumentNameB = " + argumentNameA);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Check the assert that both collections has the same size and are not
     * null.
     *
     * @param <T> the generic type
     * @param arrayA the array A
     * @param arrayB the array B
     * @param argumentNameA            the argument name a
     * @param argumentNameB            the argument name b
     */
    public static <T> void sameSizeArgument(final T[] arrayA, final T[] arrayB, final String argumentNameA,
            final String argumentNameB) {
        Check.notNullArgument(arrayA, argumentNameA);
        Check.notNullArgument(arrayB, argumentNameB);
        Check.notNullArgument(argumentNameA, "argumentNameA");
        Check.notNullArgument(argumentNameB, "argumentNameB");

        if (arrayA.length != arrayB.length) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - arrays have different length: " + argumentNameA + ".length = " + arrayA.length //$NON-NLS-1$
                            + " , " + argumentNameB + ".length = " + arrayB.length + "\n argumentNameA = "
                            + argumentNameA + ",\n argumentNameB = " + argumentNameA);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Min one element argument.
     *
     * @param <T> the generic type
     * @param array the array
     * @param argumentName the argument name
     */
    public static <T> void minOneElementArgument(final T[] array, final String argumentName) {
        Check.notNullArgument(array, argumentName);
        Check.notNullArgument(argumentName, "argumentName");

        if (array.length < 1) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException("[Assertion failed] - argument array " //$NON-NLS-1$
                    + argumentName + " should have one or more element(s) - but it is empty");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Min one element argument.
     *
     * @param <T> the generic type
     * @param collection the collection
     * @param argumentName the argument name
     */
    public static <T> void minOneElementArgument(final Collection<T> collection, final String argumentName) {
        Check.notNullArgument(collection, argumentName);
        Check.notNullArgument(argumentName, "argumentName");

        if (collection.size() < 1) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                    "[Assertion failed] - argument colection " + argumentName //$NON-NLS-1$
                            + " should have one or more element(s) - but it is empty");
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Make sure that the argument is equals to the expected object. If the
     * expected object is null then the argument have to be null too.
     *
     * @param <T> the generic type
     * @param expected            the expected
     * @param argument            the argument
     * @param argumentName            the argument name
     */
    public static <T> void equalsArgument(final T expected, final T argument, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");

        // Ralph sagt: deckt auch den Fall ab, dass beide null sind!!!
        // Beachten!!!!!!
        if (expected == argument) {
            return;
        }
        if ((expected != null) && (!expected.equals(argument))) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException("[Assertion failed] - argument " //$NON-NLS-1$
                    + argumentName + " is not equals to " + expected + ", it was " + argument);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Make sure that the argument, if it is not null, is equals to the expected
     * object. If the expected object is null then the argument have to be null
     * too. If the argument is null, then this assertion is allways true.
     *
     * @param <T> the generic type
     * @param expected            the expected
     * @param argument            the argument
     * @param argumentName            the argument name
     */
    public static <T> void equalsOrNullArgument(final T expected, final T argument, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");

        if (argument != null) {
            Check.equalsArgument(expected, argument, argumentName);
        }
    }

    /**
     * Make sure that the argument is equals to the expected object. If the
     * expected object is null then the argument have to be null too.
     *
     * @param <T> the generic type
     * @param expected            the expected
     * @param argument            the argument
     * @param argumentName            the argument name
     */
    public static <T> void equalsArgument(final boolean expected, final boolean argument, final String argumentName) {
        Check.notNullArgument(argumentName, "argumentName");

        if (expected != argument) {
            IllegalArgumentException illegalArgExc = new IllegalArgumentException("[Assertion failed] - argument " //$NON-NLS-1$
                    + argumentName + " is not equals to " + expected + ", it was " + argument);
            if (Check.activeArgumentCheck) {
                throw illegalArgExc;
            } else {
                Check.alternativeFailureAction(illegalArgExc);
            }
        }
    }

    /**
     * Check that the collection is empty.
     *
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param s the s
     */
    public static void isEmpty(final String message, final String s) {
        Check.notNullArgument(s, "s");
        if (!s.isEmpty()) {
            Check.failCompare(Check.format(message, "[Assertion failed] - empty string expected"), "", s);
        }
    }

    /**
     * ******
     * 
     * 
     * 
     * 
     * collection stuff
     * 
     * 
     * 
     * 
     * 
     * ****.
     * 
     * @param message
     *            the message
     * @param o1
     *            the o1
     * @param o2
     *            the o2
     */

    /**
     * Assert that both objects are not equals.
     * 
     * @param o1
     *            the fist object
     * @param o2
     *            the second object
     * @param message
     *            additional message for the failure description when the check
     *            fails - can be {@code null}
     */
    public static void notEquals(final String message, final Object o1, final Object o2) {
        if (o1 == o2) {
            Check.fail(Check.format(message, "[Assertion failed] - both objects are same but should not be"));
        }
        if ((o1 == null) ^ (o2 == null)) {
            return;
        }

        assert (o1 != null);
        assert (o2 != null);

        if (o1.equals(o2)) {
            Check.fail(Check.format(message, "[Assertion failed] - both objects are equals but should not be")); //$NON-NLS-1$
        }
        return;
    }

    /**
     * Assert that both objects are not equals.
     * 
     * @param o1
     *            the fist object
     * @param o2
     *            the second object
     */
    public static void notEquals(final Object o1, final Object o2) {
        Check.notEquals(null, o1, o2);
    }

    /**
     * Asserts that two Strings are equal if there whitespace are striped.
     *
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null} - can be null
     * @param expected            the expected
     * @param found            the found
     */
    public static void equalsWithoutWhitespace(final String message, final String expected, final String found) {
        Check.notNullArgument(expected, "expected");
        Check.notNullArgument(found, "found");
        String expectedNormalized = expected.replace(" ", "");
        String foundNormalized = found.replace(" ", "");

        if (!expectedNormalized.equals(foundNormalized)) {
            Check.failCompare(Check.format(message, "[Assertion failed] - trimed strings are not equal"),
                    expectedNormalized,
                    foundNormalized);
        }
    }

    /**
     * Asserts that two Strings are equal if there whitespace are striped.
     * 
     * @param expected
     *            the expected
     * @param found
     *            the found
     */
    public static void equalsWithoutWhitespace(final String expected, final String found) {
        Check.equalsWithoutWhitespace(null, expected, found);
    }

    /**
     * Check that the collection is empty.
     *
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param collection            the collection
     */
    public static void isEmpty(final String message, final Collection<?> collection) {
        Check.notNullArgument(collection, "collection");
        if (collection.size() != 0) {
            Check.failCompare(Check.format(message, "[Assertion failed] - no elements excected"), 0, collection.size());
        }
    }

    /**
     * Checks if is empty.
     * 
     * @param collection
     *            the collection
     */
    public static void isEmpty(final Collection<?> collection) {
        Check.isEmpty(null, collection);
    }

    /**
     * Checks for the correct size size.
     *
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expectedSize            the expected size
     * @param foundCollection            the collection
     */
    public static void hasSize(final String message, final int expectedSize, final Collection<?> foundCollection) {
        Check.notNullArgument(foundCollection, "collection");

        if (expectedSize != foundCollection.size()) {
            Check.failCompare(Check.format(message, "[Assertion failed] - collection has wrong size"),
                    expectedSize,
                    foundCollection.size());
        }
    }

    /**
     * Checks for the correct size size.
     * 
     * @param size
     *            the expected size
     * @param collection
     *            the collection
     */
    public static void hasSize(final int size, final Collection<?> collection) {
        Check.hasSize(null, size, collection);
    }

    /**
     * Checks for the correct size size. Checks for {@code foundCollection.size
     * >= minExpectedSize}
     *
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param minExpectedSize            the minimal expected size (this value is allowed)
     * @param foundCollection            the collection
     */
    public static void hasSizeAtLeast(final String message, final int minExpectedSize,
            final Collection<?> foundCollection) {
        Check.notNullArgument(foundCollection, "collection");

        if (foundCollection.size() < minExpectedSize) {
            Check.failCompare(Check.format(message, "[Assertion failed] - collection have not minimal size"),
                    minExpectedSize,
                    foundCollection.size());
        }
    }

    /**
     * Checks for the correct size size. Checks for {@code collection.size >=
     * minExpectedSize}
     * 
     * @param minExpectedSize
     *            the minimal expected size (this value is allowed)
     * @param collection
     *            the collection
     */
    public static void hasSizeAtLeast(final int minExpectedSize, final Collection<?> collection) {
        Check.hasSizeAtLeast(null, minExpectedSize, collection);
    }

    /**
     * Checks hat both collections have the same size.
     *
     * @param <T> the generic type
     * @param <K> the key type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expected            the expected collection (size)
     * @param found            the found collection (size)
     */
    public static <T, K> void sameSize(final String message, final Collection<T> expected, final Collection<K> found) {
        Check.notNullArgument(expected, "expected");
        Check.notNullArgument(found, "found");

        if (found.size() != expected.size()) {
            Check.failCompare(Check.format(message,
                    "[Assertion failed] - collections does not habe the same size - expected collection=" + expected
                            + " found collection=" + found),
                    expected.size(),
                    found.size());
        }
    }

    /**
     * Checks hat both collections have the same size.
     * 
     * @param <T> the generic type
     * @param <K> the generic type
     * @param expected
     *            the expected collection (size)
     * @param found
     *            the found collection (size)
     */
    public static <T, K> void sameSize(final Collection<T> expected, final Collection<K> found) {
        Check.sameSize(null, expected, found);
    }

    /**
     * Check that the two collections contains exactly equals elements. The
     * order doesn't matter.
     *
     * @param <T> the generic type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expected            one collection
     * @param found            the other collection
     */
    public static <T> void containsExact(final String message, final Collection<? extends T> expected,
            final Collection<? extends T> found) {
        Check.notNullArgument(expected, "expected");
        Check.notNullArgument(found, "found");

        Check.sameSize(message, expected, found);
        for (T expectedItem : expected) {
            if (!found.contains(expectedItem)) {
                Check.failCompare(
                        Check.format(message,
                                "[Assertion failed] - collection + " + found + " does not contrain " + expectedItem),
                        expected,
                        found);
            }
        }
    }

    /**
     * Contains exact.
     * 
     * @param <T> the generic type
     * @param expected
     *            the expected
     * @param found
     *            the found
     */
    public static <T> void containsExact(final Collection<? extends T> expected, final Collection<? extends T> found) {
        Check.containsExact(null, expected, found);
    }

    /**
     * Check that the collection contains exactly the one element.
     *
     * @param <T> the generic type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expectedItem            the expected item can be {@code null}
     * @param found            the found
     */
    public static <T> void containsExact(final String message, final T expectedItem, final Collection<T> found) {
        /* expectedItem can be null */
        Check.notNullArgument(found, "found");

        if (found.size() != 1) {
            Check.failCompare(Check.format(message, "[Assertion failed] - collection does not have exactly one item"),
                    expectedItem,
                    found);
        }
        if (!found.contains(expectedItem)) {
            Check.failCompare(
                    Check.format(message, "[Assertion failed] - collection does not contrain expected element"),
                    expectedItem,
                    found);
        }
    }

    /**
     * Contains exact.
     * 
     * @param <T> the generic type
     * @param expectedItem
     *            the expected item can be {@code null}
     * @param found
     *            the found
     */
    public static <T> void containsExact(final T expectedItem, final Collection<T> found) {
        Check.containsExact(null, expectedItem, found);
    }

    /**
     * Check that the two collections contains equals (by a specific definition)
     * elements. The order doesn't matter.
     * 
     * @param <T> the generic type
     * @param <K> the generic type
     * @param message
     *            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expected
     *            one collection
     * @param found
     *            the found
     * @param equalsChecker
     *            the equals checker
     */
    public static <T, K> void containsExact(final String message, final Collection<T> expected,
            final Collection<K> found, final EqualsChecker<T, K> equalsChecker) {
        Check.notNullArgument(expected, "expected");
        Check.notNullArgument(found, "found");
        Check.notNullArgument(equalsChecker, "equalsChecker");

        Check.sameSize(message, expected, found);
        for (T expectedObject : expected) {
            boolean ok = false;
            for (K foundObject : found) {
                if (equalsChecker.equals(expectedObject, foundObject)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                Check.failCompare(Check.format(message,
                        "collections does not contain equal elements " + "first not found element=" + expectedObject),
                        expected,
                        found);
            }
        }
    }

    /**
     * Check that the two collections contains equals (by a specific definition)
     * elements. The order doesn't matter.
     * 
     * @param <T> the generic type
     * @param <K> the generic type
     * @param expected
     *            one collection
     * @param found
     *            the found
     * @param equalsChecker
     *            the equals checker
     */
    public static <T, K> void containsExact(final Collection<T> expected, final Collection<K> found,
            final EqualsChecker<T, K> equalsChecker) {
        Check.containsExact(null, expected, found, equalsChecker);
    }

    /**
     * Check that the two collections contains equals (by a specific definition)
     * elements. The order doesn't matter.
     *
     * @param <T> the generic type
     * @param <K> the generic type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expectedObject            the expected object
     * @param found            the found collection
     * @param equalsChecker            the equals checker
     */
    public static <T, K> void containsExact(final String message, final T expectedObject, final Collection<K> found,
            final EqualsChecker<T, K> equalsChecker) {
        Check.notNullArgument(found, "found");
        Check.notNullArgument(equalsChecker, "equalsChecker");

        Check.hasSize(message, 1, found);
        if (!equalsChecker.equals(expectedObject, found.iterator().next())) {
            Check.failCompare(Check.format(message, "collection does not contain expected (one) element"),
                    expectedObject,
                    found);
        }
    }

    /**
     * Check that the two collections contains equals (by a specific definition)
     * elements. The order doesn't matter.
     * 
     * @param <T> the generic type
     * @param <K> the generic type
     * @param expectedObject
     *            the expected object
     * @param found
     *            the found collection
     * @param equalsChecker
     *            the equals checker
     */
    public static <T, K> void containsExact(final T expectedObject, final Collection<K> found,
            final EqualsChecker<T, K> equalsChecker) {
        Check.containsExact(null, expectedObject, found, equalsChecker);
    }

    /**
     * Check that the two collections contains exactly equals elements in the
     * same order.
     *
     * @param <T> the generic type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expected            one collection
     * @param found            the found
     */
    public static <T> void sameOrder(final String message, final List<T> expected, final List<T> found) {
        Check.notNullArgument(expected, "expected");
        Check.notNullArgument(found, "found");

        Check.sameSize(message, expected, found);
        int size = expected.size();
        for (int i = 0; i < size; i++) {
            if (!expected.get(i).equals(found.get(i))) {
                Check.failCompare(Check.format(message,
                        "[Assertion failed] - the elements have not the same order - " + "first difference at index "
                                + i + " - expected element=" + expected.get(i) + ", found element=" + found.get(i)),
                        expected,
                        found);
            }
        }
    }

    /**
     * Check that the two collections contains exactly equals elements in the
     * same order.
     * 
     * @param <T> the generic type
     * @param expected
     *            one collection
     * @param found
     *            the found
     */
    public static <T> void sameOrder(final List<T> expected, final List<T> found) {
        Check.sameOrder(null, expected, found);
    }

    /**
     * Check that the two collections contains exactly equals (by a specific
     * definition) elements in the same order.
     *
     * @param <T> the generic type
     * @param <K> the generic type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expected            one collection
     * @param found            the found
     * @param equalsChecker            the equals definition
     */
    public static <T, K> void sameOrder(final String message, final List<T> expected, final List<K> found,
            final EqualsChecker<T, K> equalsChecker) {
        Check.notNullArgument(expected, "expected");
        Check.notNullArgument(found, "found");

        Check.sameSize(expected, found);
        int size = expected.size();
        for (int i = 0; i < size; i++) {
            try {
                if (!equalsChecker.equals(expected.get(i), found.get(i))) {
                    Check.failCompare(Check.format(message,
                            "[Assertion failed] - the elements have not the same order - "
                                    + "first difference at index " + i + " - expected element=" + expected.get(i)
                                    + ", found element=" + found.get(i)),
                            expected,
                            found);
                }
            } catch (RuntimeException e) {
                throw new IllegalArgumentException(
                        "[Exception while assertion check] - the elements have not the same order"
                                + "(first difference at index " + i + " (expected=" + expected.get(i) + " found="
                                + found.get(i) + ")) - expected list + " + expected + " found list " + found,
                        e);
            }
        }
    }

    /**
     * Check that the two collections contains exactly equals (by a specific
     * definition) elements in the same order.
     *
     * @param <T> the generic type
     * @param <K> the generic type
     * @param expected            one collection
     * @param found            the found
     * @param equalsChecker            the equals definition
     */
    public static <T, K> void sameOrder(final List<T> expected, final List<K> found,
            final EqualsChecker<T, K> equalsChecker) {
        Check.sameOrder(null, expected, found, equalsChecker);
    }

    /**
     * Check that the elements of expects are element of found too (by a
     * specific definition) elements. The order doesn't matter. {@code found}
     * can have some more elements.
     * 
     * @param <T> the generic type
     * @param <K> the generic type
     * @param message
     *            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expectedObject
     *            the expected object
     * @param found
     *            the found
     * @param equalsChecker
     *            the specific equals definition
     */
    public static <T, K> void containsAtLeast(final String message, final T expectedObject, final Collection<K> found,
            final EqualsChecker<T, K> equalsChecker) {
        Check.notNullArgument(found, "found");
        Check.notNullArgument(equalsChecker, "equalsChecker");

        for (K foundObject : found) {
            if (equalsChecker.equals(expectedObject, foundObject)) {
                return;
            }
        }
        Check.failCompare(Check.format(message, "[Assertion failed] - expected object not found in collection"),
                expectedObject,
                found);
    }

    /**
     * Check that the elements of expects are element of found too (by a
     * specific definition) elements. The order doesn't matter. {@code found}
     * can have some more elements.
     * 
     * @param <T> the generic type
     * @param <K> the generic type
     * @param expectedObject
     *            the expected object
     * @param found
     *            the found
     * @param equalsChecker
     *            the specific equals definition
     */
    public static <T, K> void containsAtLeast(final T expectedObject, final Collection<K> found,
            final EqualsChecker<T, K> equalsChecker) {
        Check.containsAtLeast(null, expectedObject, found, equalsChecker);
    }

    /**
     * Check that the elements of expects are element of found too (by a
     * specific definition) elements. The order doesn't matter. {@code found}
     * can have some more elements.
     *
     * @param <T> the generic type
     * @param <K> the generic type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expected            one collection
     * @param found            the found
     * @param equalsChecker            the specific equals definition
     */
    public static <T, K> void containsAtLeast(final String message, final Collection<T> expected,
            final Collection<K> found, final EqualsChecker<T, K> equalsChecker) {
        Check.notNullArgument(expected, "expected");
        Check.notNullArgument(found, "found");
        Check.notNullArgument(equalsChecker, "equalsChecker");

        for (T expectedObject : expected) {
            Check.containsAtLeast(message, expectedObject, found, equalsChecker);
        }
    }

    /**
     * Check that the elements of expects are element of found too (by a
     * specific definition) elements. The order doesn't matter. {@code found}
     * can have some more elements.
     *
     * @param <T> the generic type
     * @param <K> the generic type
     * @param expected            one collection
     * @param found            the found
     * @param equalsChecker            the specific equals definition
     */
    public static <T, K> void containsAtLeast(final Collection<T> expected, final Collection<K> found,
            final EqualsChecker<T, K> equalsChecker) {
        Check.containsAtLeast(null, expected, found, equalsChecker);
    }

    /**
     * Assert that the collection contains the item. The Collection can have
     * other items too.
     *
     * @param <T> the generic type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param expectedItem            the expected item
     * @param found            the found
     */
    public static <T> void contains(final String message, final T expectedItem, final Collection<T> found) {
        Check.notNullArgument(found, "found");

        if (!found.contains(expectedItem)) {
            Check.failCompare(Check.format(message, "[Assertion failed] - collection does not contrain expected item"),
                    expectedItem,
                    found);
        }
    }

    /**
     * Contains.
     * 
     * @param <T> the generic type
     * @param expectedItem
     *            the expected item
     * @param found
     *            the found
     */
    public static <T> void contains(final T expectedItem, final Collection<T> found) {
        Check.contains(null, expectedItem, found);
    }

    /**
     * Assert that the collection contains the expected items. The Collection
     * can have other items too.
     *
     * @param <T> the generic type
     * @param expectedItems            the expected items
     * @param found            the found
     */
    public static <T> void contains(final Collection<T> expectedItems, final Collection<T> found) {
        Check.notNullArgument(expectedItems, "expectedItems");
        Check.notNullArgument(found, "found");

        for (T exptetedItem : expectedItems) {
            Check.contains(exptetedItem, found);
        }
    }

    /**
     * Assert that the collection does not contains the item. The Collection can
     * have other items.
     *
     * @param <T> the generic type
     * @param message            additional message for the failure description when the check
     *            fails - can be {@code null}
     * @param notExpectedItem            the not expected item
     * @param found            the found
     */
    public static <T> void containsNot(final String message, final T notExpectedItem, final Set<T> found) {
        Check.notNullArgument(found, "found");

        if (found.contains(notExpectedItem)) {
            Check.fail(Check.format(message,
                    "[Assertion failed] - colection + " + found + " does contrain the not expected item "
                            + notExpectedItem));
        }
    }

    /**
     * Contains not.
     * 
     * @param <T> the generic type
     * @param notExpectedItem the not expected item
     * @param found the found
     */
    public static <T> void containsNot(final T notExpectedItem, final Set<T> found) {
        Check.containsNot(null, notExpectedItem, found);
    }

    /**
     * Check that there is exactly one true boolean.
     * 
     * @param message
     *            the message
     * @param value1
     *            the first boolean
     * @param value2
     *            the second boolean
     * @param values
     *            the other booleans
     */
    public static void containsExactOneTrue(final String message, final boolean value1, final boolean value2,
            final boolean... values) {
        Check.notNullArgument(values, "values");

        int trueCounter = 0;

        if (value1) {
            trueCounter++;
        }
        if (value2) {
            trueCounter++;
        }
        for (boolean value : values) {
            if (value) {
                trueCounter++;
            }
        }

        if (trueCounter != 1) {
            Check.fail(Check.format(message,
                    "[Assertion failed] - the booleans + " + value1 + ", " + value2 + ", " + values
                            + " does not contains exactly one TRUE value - " + trueCounter + " true values found"));
        }
    }

    /**
     * Check that there is exactly one true boolean.
     * 
     * @param value1
     *            the first boolean
     * @param value2
     *            the second boolean
     * @param values
     *            the other booleans
     */
    public static void containsExactOneTrue(final boolean value1, final boolean value2, final boolean... values) {
        Check.containsExactOneTrue(null, value1, value2, values);
    }

    /**
     * Check that there is zero or one NOT {@code NULL} value.
     * 
     * @param message
     *            the message
     * @param value1
     *            the first object
     * @param value2
     *            the second object
     * @param values
     *            the other objects
     */
    public static void containsZeroOrOneNotNull(final String message, final Object value1, final Object value2,
            final Object... values) {
        Check.notNullArgument(values, "values");

        int notNullCounter = 0;

        if (value1 != null) {
            notNullCounter++;
        }
        if (value2 != null) {
            notNullCounter++;
        }
        for (Object value : values) {
            if (value != null) {
                notNullCounter++;
            }
        }

        if (notNullCounter > 1) {
            Check.fail(Check.format(message,
                    "[Assertion failed] - the Objects + " + value1 + ", " + value2 + ", " + values
                            + " does not contains zero or one NOT NULL value - " + notNullCounter
                            + " not Null values found"));
        }
    }

    /**
     * CCheck that there is zero or one NOT {@code NULL} value.
     * 
     * @param value1
     *            the first object
     * @param value2
     *            the second object
     * @param values
     *            the other objects
     */
    public static void containsZeroOrOneNotNull(final Object value1, final Object value2, final Object... values) {
        Check.containsZeroOrOneNotNull(null, value1, value2, values);
    }

    /**
     * Check that there is exactly one NOT {@code NULL} value.
     * 
     * @param message
     *            the message
     * @param value1
     *            the first object
     * @param value2
     *            the second object
     * @param values
     *            the other objects
     */
    public static void containsExactOneNotNull(final String message, final Object value1, final Object value2,
            final Object... values) {
        Check.notNullArgument(values, "values");

        int notNullCounter = 0;

        if (value1 != null) {
            notNullCounter++;
        }
        if (value2 != null) {
            notNullCounter++;
        }
        for (Object value : values) {
            if (value != null) {
                notNullCounter++;
            }
        }

        if (notNullCounter != 1) {
            Check.fail(Check.format(message,
                    "[Assertion failed] - the Objects + " + value1 + ", " + value2 + ", " + values
                            + " does not contains exactly one NOT NULL value - " + notNullCounter
                            + " not Null values found"));
        }
    }

    /**
     * Check that there is exactly one NOT {@code NULL} value.
     * 
     * @param value1
     *            the first object
     * @param value2
     *            the second object
     * @param values
     *            the other objects
     */
    public static void containsExactOneNotNull(final Object value1, final Object value2, final Object... values) {
        Check.containsExactOneNotNull(null, value1, value2, values);
    }

    /**
     * Check that all elements of an argument colletiona are unique with respect to the equals checker.
     * 
     * @param <T> the generic type
     * @param collection the collection of proven elements 
     * @param equalsChecker used to check the equality between two elements.
     * @param argumentName the name of the argument
     */
    public static <T> void uniqueElementsArgument(final Collection<T> collection,
            final EqualsChecker<T, T> equalsChecker, final String argumentName) {
        Check.notNullArgument(collection, "collection");
        Check.notNullArgument(equalsChecker, "equalsChecker");
        Check.notNullArgument(argumentName, "argumentName");

        List<T> elements = new ArrayList<T>(collection);
        int size = elements.size();
        for (int i = 0; i < size; i++) {
            T elementI = elements.get(i);
            for (int k = i + 1; k < size; k++) {
                T elementK = elements.get(k);
                if (equalsChecker.equals(elementI, elementK)) {

                    IllegalArgumentException illegalArgExc = new IllegalArgumentException(
                            "[Assertion failed] - collection on argument <code>" + argumentName //$NON-NLS-1$
                                    + "</code> has not unique element -  two (or more) elements are equals with respect to an equals definition - equals element[" //$NON-NLS-1$
                                    + i + "]: " + elementI + ", equals element[" + k + "]: " + elementK //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                                    + ", collection=" + collection);
                    if (Check.activeArgumentCheck) {
                        throw illegalArgExc;
                    } else {
                        Check.alternativeFailureAction(illegalArgExc);
                    }
                }
            }
        }
    }

    /**
     * Check that all elements of an argument collections are unique with respect to the equals checker.
     * 
     * @param <T> the generic type
     * @param collection the collection of proven elements
     * @param argumentName the name of the argument
     */
    public static <T> void uniqueElementsArgument(final Collection<T> collection, final String argumentName) {
        Check.notNullArgument(collection, "collection");
        Check.notNullArgument(argumentName, "argumentName");

        uniqueElementsArgument(collection, NativeEqualsChecker.<T> getInstance(), argumentName);
    }

    /**
     * Check that all elements are unique with respect to the equals checker.
     * 
     * @param <T> the generic type
     * @param message a message if the check fails
     * @param collection the collection of proven elements 
     * @param equalsChecker used to check the equality between two elements.
     */
    public static <T> void uniqueElements(final String message, final Collection<T> collection,
            final EqualsChecker<T, T> equalsChecker) {
        Check.notNullArgument(collection, "collection");
        Check.notNullArgument(equalsChecker, "equalsChecker");

        List<T> elements = new ArrayList<T>(collection);
        int size = elements.size();
        for (int i = 0; i < size; i++) {
            T elementI = elements.get(i);
            for (int k = i + 1; k < size; k++) {
                T elementK = elements.get(k);
                if (equalsChecker.equals(elementI, elementK)) {
                    Check.fail(Check.format(message,
                            "[Assertion failed] - collection has not unique element -  two (or more) elements are equals with respect to an equals definition - equals element["
                                    + i + "]: " + elementI + ", equals element[" + k + "]: " + elementK
                                    + ", collection=" + collection));
                }
            }
        }
    }

    /**
     * Check that all elements are unique with respect to the equals checker.
     * 
     * @param <T> the generic type
     * @param collection the collection of proven elements 
     * @param equalsChecker used to check the equality between two elements.
     */
    public static <T> void uniqueElements(final Collection<T> collection, final EqualsChecker<T, T> equalsChecker) {
        Check.notNullArgument(collection, "collection");
        Check.notNullArgument(equalsChecker, "equalsChecker");

        uniqueElements(null, collection, equalsChecker);
    }

    /**
     * Check that all elements are unique.
     * 
     * @param <T> the generic type
     * @param message a message if the check fails
     * @param collection the collection of proven elements 
     */
    public static <T> void uniqueElements(final String message, final Collection<T> collection) {
        Check.notNullArgument(collection, "collection");

        uniqueElements(null, collection, NativeEqualsChecker.<T> getInstance());
    }

    /**
     * Check that all elements are unique.
     *
     * @param <T> the generic type
     * @param collection the collection of proven elements
     */
    public static <T> void uniqueElements(final Collection<T> collection) {
        Check.notNullArgument(collection, "collection");

        uniqueElements(null, collection);
    }

    /**
     * Fails a test with the given message.
     * 
     * @param message
     *            failure description - can be {@code null}
     */
    static public void fail(final String message) {
        throw new ConstraintViolationException(message);
    }

    /**
     * Fail compare.
     * 
     * @param message
     *            the failure description - can be {@code null}
     * @param expected
     *            the expected
     * @param actual
     *            the actual
     */
    static public void failCompare(final String message, final String expected, final String actual) {
        throw new ConstraintViolationException(message + " expected <" + expected + "> but was <" + actual + ">");
    }

    /**
     * Fail compare.
     * 
     * @param message
     *            additional message for the failure description - can be
     *            {@code null}
     * @param expected
     *            the expected
     * @param actual
     *            the actual
     */
    static public void failCompare(final String message, final int expected, final int actual) {
        Check.failCompare(message, Integer.toString(expected), Integer.toString(actual));
    }

    /**
     * Fail compare.
     * 
     * @param message
     *            additional message for the failure description - can be
     *            {@code null}
     * @param expected
     *            the expected
     * @param actual
     *            the actual
     */
    static public void failCompare(final String message, final Collection<?> expected, final Collection<?> actual) {
        Check.failCompare(message, expected.toString(), actual.toString());
    }

    /**
     * Fail compare.
     * 
     * @param message
     *            additional message for the failure description - can be
     *            {@code null}
     * @param expected
     *            the expected
     * @param actual
     *            the actual
     */
    static public void failCompare(final String message, final Object expected, final Collection<?> actual) {
        Check.failCompare(message, expected != null ? expected.toString() : null, actual.toString());
    }

    /**
     * Format.
     * 
     * @param message
     *            additional message for the failure description - can be
     *            {@code null}
     * @param cause
     *            the cause
     * 
     * @return the string
     */
    public static String format(final String message, final String cause) {
        /* message can be null */
        Check.notNullArgument(cause, "cause");
        if (message == null) {
            return cause;
        } else {
            return message + " " + cause;
        }
    }
}
