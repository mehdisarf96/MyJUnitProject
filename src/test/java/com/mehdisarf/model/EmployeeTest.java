package com.mehdisarf.model;

import com.mehdisarf.exceptions.UnfairSalaryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testConstructor_WHEN_illegalArgumentExceptionThrown() {

        assertThrows(IllegalArgumentException.class,
                () -> new Employee("mehdi", "sar  f", "0010010017", 2000.0)
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Employee("mehdi4", "sarf", "0010010017", 2000.0)
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Employee("mehdi", "sarf", null, 2000.0)
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Employee("mehdi", "sarf", "0010010017", null)
        );
        assertThrows(UnfairSalaryException.class,
                () -> new Employee("mehdi", "sarf", "0010010017", 990.0)
        );
    }

    @ParameterizedTest
    @MethodSource("populateInvalidArguments")
    void parameterizedTestConstructor_WHEN_illegalArgumentExceptionThrown(String firstName,
                                                                          String lastName,
                                                                          String nationalId,
                                                                          Double salary) {
        assertThrows(IllegalArgumentException.class, () -> new Employee(firstName, lastName, nationalId, salary));
    }

    @ParameterizedTest
    @MethodSource("populateInvalidArguments")
    void secondParameterizedTestConstructor_WHEN_illegalArgumentExceptionThrown(String firstName,
                                                                                String lastName,
                                                                                String nationalId,
                                                                                Double salary) {
        // ba AssertJ
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Employee(firstName, lastName, nationalId, salary))
                .withNoCause();

//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
//                () -> new Employee(firstName, lastName, nationalId, salary));
//        assertNull(ex.getCause());
//        assertNull(ex.getMessage());
    }

    public static Stream<Arguments> populateInvalidArguments() {
        return Stream.of(
                Arguments.arguments("mehdi", "sar  f", "0010010017", 2000.0),
                Arguments.arguments("mehdi4", "sarf", "0010010017", 2000.0),
                Arguments.arguments("mehdi", "sarf", null, 2000.0),
                Arguments.arguments("mehdi", "sarf", "0010010017", null)
        );
    }

    public static Object[][] secondVersionOfPopulateInvalidArguments() { // kollan in methodsource kheyli chiza can return.
        // faqat Stream of Arguments nist.
        return new Object[][]{
                {"mehdi", "sar  f", "0010010017", 2000.0},
                {"mehdi4", "sarf", "0010010017", 2000.0},
                {"mehdi", "sarf", null, 2000.0},
                {"mehdi", "sarf", "0010010017", null}
        };
    }

    @Test
    void testConstructor_WHEN_argumentsAreValid() {
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);
        assertEquals("mehdi", employee.getFirstName());
        assertEquals("sarf", employee.getLastName());
        assertEquals("0010010017", employee.getNationalId());

        // assertIterableEquals(employee.DEFAULT_WORKING_DAYS,employee.getWorkingDays()); // pass nemishe in. (Set e.)
        assertThat(employee.getWorkingDays()).containsAll(Employee.DEFAULT_WORKING_DAYS);
        assertThat(employee.getWorkingDays())
                .containsExactlyInAnyOrderElementsOf(Employee.DEFAULT_WORKING_DAYS);
    }
}