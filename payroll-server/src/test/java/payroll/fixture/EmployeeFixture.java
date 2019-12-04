package payroll.fixture;

import payroll.employeeontext.domain.Email;
import payroll.employeeontext.domain.Employee;
import payroll.employeeontext.domain.EmployeeId;
import payroll.employeeontext.domain.EmployeeType;
import payroll.payrollcontext.domain.Currency;
import payroll.payrollcontext.domain.Salary;
import payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import payroll.payrollcontext.domain.hourlyemployee.TimeCard;
import payroll.payrollcontext.domain.salariedemployee.Absence;
import payroll.payrollcontext.domain.salariedemployee.LeaveReason;
import payroll.payrollcontext.domain.salariedemployee.SalariedEmployee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class EmployeeFixture {
    private final static Salary salaryOfHour = Salary.of(100, Currency.RMB);

    public static HourlyEmployee hourlyEmployeeOf(String employeeId, int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        final List<TimeCard> timeCards = createTimeCards(workHours1, workHours2, workHours3, workHours4, workHours5);
        return new HourlyEmployee(EmployeeId.of(employeeId), salaryOfHour, timeCards);
    }

    public static HourlyEmployee hourlyEmployeeOf(String employeeId, List<TimeCard> timeCards) {
        return new HourlyEmployee(EmployeeId.of(employeeId), salaryOfHour, timeCards);
    }

    public static List<TimeCard> createTimeCards(int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        final TimeCard timeCard1 = new TimeCard(LocalDate.of(2019, 11, 4), workHours1);
        final TimeCard timeCard2 = new TimeCard(LocalDate.of(2019, 11, 5), workHours2);
        final TimeCard timeCard3 = new TimeCard(LocalDate.of(2019, 11, 6), workHours3);
        final TimeCard timeCard4 = new TimeCard(LocalDate.of(2019, 11, 7), workHours4);
        final TimeCard timeCard5 = new TimeCard(LocalDate.of(2019, 11, 8), workHours5);

        final ArrayList<TimeCard> timeCards = new ArrayList<>();
        timeCards.add(timeCard1);
        timeCards.add(timeCard2);
        timeCards.add(timeCard3);
        timeCards.add(timeCard4);
        timeCards.add(timeCard5);
        return timeCards;
    }

    public static SalariedEmployee salariedEmployeeOf(String employeeId, double salary) {
        return new SalariedEmployee(EmployeeId.of(employeeId), Salary.of(salary));
    }

    public static SalariedEmployee salariedEmployeeWithOneSickLeaveOf(String employeeId, double salary) {
        Absence sickLeave = createAbsence(4, LeaveReason.SICK_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, salary, sickLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOneCasualLeaveOf(String employeeId) {
        Absence casualLeave = createAbsence(4, LeaveReason.CASUAL_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, 10000.00, casualLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOnePaidLeaveOf(String employeeId) {
        Absence paidLeave = createAbsence(4, LeaveReason.MATERNITY_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, 10000.00, paidLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOneDisapprovedLeaveOf(String employeeId) {
        Absence disapprovedLeave = createAbsence(4, LeaveReason.DISAPPROVED_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, 10000.00, disapprovedLeave);
    }

    public static SalariedEmployee salariedEmployeeWithManyLeavesOf(String employeeId) {
        Absence sickLeave = createAbsence(4, LeaveReason.SICK_LEAVE);
        Absence casualLeave = createAbsence(5, LeaveReason.CASUAL_LEAVE);
        Absence paidLeave = createAbsence(6, LeaveReason.MATERNITY_LEAVE);
        Absence disapprovedLeave = createAbsence(7, LeaveReason.DISAPPROVED_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, 10000.00, sickLeave, casualLeave, paidLeave, disapprovedLeave);
    }

    private static Absence createAbsence(int dayOfMonth, LeaveReason sickLeave) {
        return new Absence(LocalDate.of(2019, 11, dayOfMonth), sickLeave);
    }

    private static SalariedEmployee createSalariedEmployeeWithAbsences(String employeeId, double salary, Absence... leaves) {
        return new SalariedEmployee(EmployeeId.of(employeeId), Salary.of(salary), new ArrayList<>(asList(leaves)));
    }

    public static Employee employeeOf(String employeeId, String name, String email, EmployeeType employeeType) {
        return new Employee(EmployeeId.of(employeeId), name, new Email(email), employeeType, LocalDate.now());
    }
}