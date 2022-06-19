package com.mehdisarf.model;

import com.mehdisarf.exceptions.UnfairSalaryException;
import com.mehdisarf.model.util.WorkingDaysAggregator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    /*
        here I've tested parameterized constructor of Employee class.
     */
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

//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
//                () -> new Employee(firstName, lastName, nationalId, salary));
//        assertNull(ex.getCause());
//        assertNull(ex.getMessage());

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

    @ParameterizedTest
    @CsvFileSource(resources = "/employee/constructor-illegal-arguments.csv")
    void parameterizedTestConstructorUsingACsvFile_WHEN_illegalArgumentExceptionThrown(String firstName,
                                                                                       String lastName,
                                                                                       String nationalId,
                                                                                       Double salary) {
        assertThrows(IllegalArgumentException.class, () -> new Employee(firstName, lastName, nationalId, salary));
    }

    // use accessor
    @ParameterizedTest
    @CsvFileSource(resources = "/employee/constructor-illegal-arguments.csv")
    void parameterizedTestConstructorUsingACsvFile_WHEN_illegalArgumentExceptionThrown(ArgumentsAccessor accessor) {
        System.out.println(accessor.get(0) + "," + accessor.get(1) + "," + accessor.get(2) + "," + accessor.get(3));
    }

    // use accessor
    @ParameterizedTest
    @MethodSource("populateInvalidArguments")
    void parameterizedTestConstructor_WHEN_illegalArgumentExceptionThrown(ArgumentsAccessor accessor) {
        System.out.println(accessor.get(0) + "," + accessor.get(1) + "," + accessor.get(2) + "," + accessor.get(3));
    }

    /*
    @ParameterizedTest
    public void parameterizedTestSetWorkingDays_WHEN_baseChoice_THEN_newWorkingDaysSet(List<DayOfWeek> newWorkingDays) {}

    vase chenin chizi nemtini az CSVfile estefade koni mostaqim be onvane source.
    chon CSV text e va object o ina halish nist. pas az chi bayad estefade kard?
    bayad ye chizi dashte bashi ke bere az source et (tu in case csv) bekhune va maqadir ro berize tuye yek object
    (vase in case, tuye yek ListOfWeek),
    az Aggregator.
    chetor? (un class e Aggregator ye methodi dare ke Object barmigardune pas mituni bash harchizi ro return koni)
    kari ke to mikoni ine ke miay ba ye accessor (ArgumentsAccessor), element ha ro az source et mikhuni,
    va bad berizishun tuye ye object ii. (tajmieshun koni tuye ye object) va bad un object ro bargarduni.
    un object ii ke tavasote aggregator bargardunde shode (man besh migam aggregated object), un be onvane
    argument rikhte mishe tu parameter method e ma. chetor besh begim to kodum parameter rikhte she? ba @AggregateWith
    (An ArgumentsAggregator is applied to a method parameter of a @ParameterizedTest method via the @AggregateWith.)
    (The result of the aggregation will be passed as an argument to the @ParameterizedTest method for the annotated parameter.)
    (@AggregateWith: for an aggregated value to be resolved for the annotated parameter when the test method is invoked.)

    So bayad ye class e Aggregator besazim.
    in class bayad interface e ArgumentAggregator ro impl kone. ba method e 'aggregateArguments' esh
    mitunim argument ha ro aggregate konim o tuye ye object bargardunim. az esmesh ham malume.
    aggregate mikone arguments ha ro.
*/

    @ParameterizedTest
    @CsvFileSource(resources = "/employee/day-of-week.csv")
    void testConstructorUsingCsvWithAggregator(@AggregateWith(WorkingDaysAggregator.class) List<DayOfWeek> newWorkingDays) {
        System.out.println(newWorkingDays);
        //assertThrows(IllegalArgumentException.class, () -> new Employee(firstName, lastName, nationalId, salary));
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
    /*
        End of Employee's constructor testing methods.
     */

    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------

    /*
        here I've tested setWorkingDays() method of Employee class.
        I've written these test using input space partitioning.
        and here I use 'Base-Choice coverage'.

        harbar method e setWorkingDays() ro bar mabnaye yeki az khosusiat e zir test kon.
     */

    // efraz e input bar mabnaye NULL budan ya Null nabudan
    // base choice: Null Nabudan.
    // efraz e input bar mabnaye inke includes Thursday or not.
    // base choice: not including Thursday.
    // efraz e input bar mabnaye inke includes Friday or not.
    // base choice: not including Friday.
    // efraz e input bar mabnaye size of collection. (0, 1-2, 3, greater than 3)
    // base choice: greater than 3 or 3.
    // efraz e input bar mabnaye inke includes duplicated elements or not.
    // base choice: not including duplicate elements.

    // efraz e input bar mabnaye NULL budan ya Null nabudan
    // base choice: Null Nabudan.
    @Test
    void setWorkingDays_WHEN_notNull() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.SUNDAY, DayOfWeek.WEDNESDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        assertDoesNotThrow(() -> employee.setWorkingDays(newWorkingDays));
    }

    @Test
    void setWorkingDays_WHEN_null_THEN_throwsIllegalArgumentException() {
        List<DayOfWeek> newWorkingDays = null;
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        assertThrows(IllegalArgumentException.class, () -> employee.setWorkingDays(newWorkingDays));
    }

    // efraz e input bar mabnaye inke includes Thursday or not.
    // base choice: not including Thursday.
    @Test
    public void testSetWorkingDays_WHEN_workingDaysNotIncludeThursday() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        employee.setWorkingDays(newWorkingDays);

        Assertions.assertThat(employee.getWorkingDays())
                .containsExactlyInAnyOrderElementsOf(newWorkingDays);
    }

    @Test
    public void testSetWorkingDays_WHEN_workingDaysIncludeThursday_THEN_throwsIllegalArgumentException() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        assertThrows(IllegalArgumentException.class,
                () -> employee.setWorkingDays(newWorkingDays));
    }

    // efraz e input bar mabnaye inke includes Friday or not.
    // base choice: not including Friday.
    @Test
    public void testSetWorkingDays_WHEN_workingDaysNotIncludeFriday() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        employee.setWorkingDays(newWorkingDays);

        Assertions.assertThat(employee.getWorkingDays())
                .containsExactlyInAnyOrderElementsOf(newWorkingDays);
    }

    @Test
    public void testSetWorkingDays_WHEN_workingDaysIncludeFriday_THEN_throwsIllegalArgumentException() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        assertThrows(IllegalArgumentException.class,
                () -> employee.setWorkingDays(newWorkingDays));
    }

    // efraz e input bar mabnaye size of collection. (0, 1-2, 3, greater than 3)
    // base choice: greater than 3 or 3.
    @Test
    public void testSetWorkingDays_WHEN_workingDaysContainsAtLeastThreeDays() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        employee.setWorkingDays(newWorkingDays);

        Assertions.assertThat(employee.getWorkingDays())
                .containsExactlyInAnyOrderElementsOf(newWorkingDays);
    }

    @Test
    public void testSetWorkingDays_WHEN_workingDaysContainLessThanThreeDays_THEN_throwsIllegalArgumentException() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        assertThrows(IllegalArgumentException.class,
                () -> employee.setWorkingDays(newWorkingDays));
    }

    // efraz e input bar mabnaye inke includes duplicated elements or not.
    // base choice: not including duplicate elements.
    @Test
    public void testSetWorkingDays_WHEN_workingDaysNotIncludeDuplicateElements() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        List<DayOfWeek> secondNewWorkingDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.SUNDAY);
        // un not including duplicate elements, yani zamani ke 3ta element dashte bashim
        // va tush duplicate bashe. vaqti bishtare 3ta day darim va tush duplicate hast
        // moshkeli nist chon code handle karde va mirizashun tuye ye Set.
        // pas in testcase ke duplicate darim va bishtar e 3onsor, in halate valid ii hast.
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        employee.setWorkingDays(newWorkingDays);

        Assertions.assertThat(employee.getWorkingDays()).containsAll(newWorkingDays);
        Assertions.assertThat(employee.getWorkingDays()).containsAll(secondNewWorkingDays);
    }

    @Test
    public void testSetWorkingDays_WHEN_workingDaysIncludeDuplicateElements() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.SUNDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        assertThrows(IllegalArgumentException.class, () -> employee.setWorkingDays(newWorkingDays));
    }

    // efraz e input bar mabnaye tarkib e hameye khosusiat e qabl.
    // base choice:
    // Null Nabudan & not including Thursday
    // & not including Friday & having size greater than 3 or 3 & not including duplicate elements.
    @Test
    public void testSetWorkingDays_WHEN_baseChoice_THEN_newWorkingDaysSet() {
        List<DayOfWeek> newWorkingDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY);
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        employee.setWorkingDays(newWorkingDays);

        Assertions.assertThat(employee.getWorkingDays())
                .containsExactlyInAnyOrderElementsOf(newWorkingDays);
    }

    @ParameterizedTest
    @MethodSource("populateValidworkingDays")
    public void parameterizedTestSetWorkingDays_WHEN_baseChoice_THEN_newWorkingDaysSet(List<DayOfWeek> newWorkingDays) {
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        employee.setWorkingDays(newWorkingDays);

        Assertions.assertThat(employee.getWorkingDays())
                .containsAll(newWorkingDays);
    }

    public static Stream<List<DayOfWeek>> populateValidworkingDays() {
        return Stream.of(
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY),
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.SUNDAY),
                // un not including duplicate elements, yani zamani ke 3ta element dashte bashim
                // va tush duplicate bashe. vaqti bishtare 3ta day darim va tush duplicate hast
                // moshkeli nist chon code handle karde va mirizashun tuye ye Set.
                // pas in testcase ke duplicate darim va bishtar e 3onsor, in halate valid ii hast.
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY)
        );
    }

    @ParameterizedTest
    @MethodSource("populateInvalidworkingDays")
    public void parameterizedTestSetWorkingDays_WHEN_otherThanBaseChoice_THEN_throwsIllegalArgumentException(List<DayOfWeek> newWorkingDays) {
        Employee employee = new Employee("mehdi", "sarf", "0010010017", 1200.0);

        assertThrows(IllegalArgumentException.class, () -> employee.setWorkingDays(newWorkingDays));
    }

    public static Stream<List<DayOfWeek>> populateInvalidworkingDays() {
        return Stream.of(
                null,
                Arrays.asList(null, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY),
                Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY),
                Arrays.asList(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.TUESDAY),
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                Arrays.asList(DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY, DayOfWeek.SUNDAY)
        );
    }


}