package com.mehdisarf.model;

import com.mehdisarf.exceptions.UnfairSalaryException;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.mehdisarf.StringUtil.isName;
import static com.mehdisarf.StringUtil.isNationalId;
import static java.time.DayOfWeek.*;

@Data
public class Employee {

    public static final long MAX_OFF_DAYS_PER_YEAR = 32;
    public static final int MIN_WORKING_DAYS_PER_WEEK = 3;
    public static final double BASE_SALARY = 1000.0;
    public static final List<DayOfWeek> DEFAULT_WORKING_DAYS = Arrays.asList(MONDAY, TUESDAY, SUNDAY, WEDNESDAY, SATURDAY);

    private final String firstName;
    private final String lastName;
    private final String nationalId;
    private Double salary;
    private Set<DayOfWeek> workingDays;
    private Set<LocalDate> offDays;

    public Employee(String firstName, String lastName, String nationalId) {
        this(firstName, lastName, nationalId, BASE_SALARY);
    }

    public Employee(String firstName, String lastName, String nationalId, Double salary) {
        if (!isName(firstName) || !isName(lastName) || !isNationalId(nationalId))
            throw new IllegalArgumentException();

        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        setSalary(salary);
        setDefaultWorkingDays();
        this.offDays = new HashSet<>();
    }

    private void setSalary(Double salary) {
        if (Objects.isNull(salary))
            throw new IllegalArgumentException("Salary could not be null");
        if (salary <= BASE_SALARY)
            throw new UnfairSalaryException("Salary could not be less than base salary");
        this.salary = salary;
    }

    private void setDefaultWorkingDays() {
        workingDays = new HashSet<>(DEFAULT_WORKING_DAYS);
    }

    public List<DayOfWeek> getSortedWorkingDays() {
        return workingDays.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<LocalDate> getSortedOffDays() {
        return offDays.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public void setWorkingDays(Collection<DayOfWeek> workingDays) {
        validateWorkingDays(workingDays);
        this.workingDays = new HashSet<>(workingDays);
        fixOffDaysAfterUpdatingWorkingDays();
    }

    private void fixOffDaysAfterUpdatingWorkingDays() {
        Set<LocalDate> toBeDeleted = this.offDays.stream()
                .filter(l -> l.isAfter(LocalDate.now()))
                .filter(l -> !isWorkingDate(l))
                .collect(Collectors.toSet());
        this.offDays.removeAll(toBeDeleted);
    }

    private void validateWorkingDays(Collection<DayOfWeek> workingDays) {
        if (Objects.isNull(workingDays))
            throw new IllegalArgumentException("Working days could not be null");
        if ((workingDays.contains(THURSDAY)) || workingDays.contains(FRIDAY))
            throw new IllegalArgumentException("Working days could not contain weekend.");
        if (workingDays.stream().distinct().count() < MIN_WORKING_DAYS_PER_WEEK)
            throw new IllegalArgumentException("Working days should contain more than three days.");
        if (workingDays.stream().anyMatch(dw -> dw == null))
            throw new IllegalArgumentException("Working days should contain null value.");
    }

    private boolean isWorkingDay(DayOfWeek day) {
        return this.workingDays.contains(day);
    }

    private boolean isWorkingDate(LocalDate date) {
        return this.isWorkingDay(date.getDayOfWeek()) && !offDays.contains(date);
    }

    public boolean addOffDay(LocalDate requestedOffDay) {
        if (getTotalOffDaysIn(requestedOffDay.getYear()) >= MAX_OFF_DAYS_PER_YEAR)
            return true;
        //if (!isWorkingDay(requestedOffDay.getDayOfWeek()) || NationalCalendar.isPublicHoliday(requestedOffDay))
//            return false;
        return offDays.add(requestedOffDay);
    }

    private long getTotalOffDaysIn(int year) {
        return offDays.stream()
                .filter(l -> l.getYear() == year)
                .count();
    }


}