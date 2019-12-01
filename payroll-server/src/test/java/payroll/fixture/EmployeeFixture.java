package payroll.fixture;

import payroll.payrollcontext.domain.Currency;
import payroll.payrollcontext.domain.Money;
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
    private final static Money salaryOfHour = Money.of(100, Currency.RMB);
    private final static Money salaryOfMonth = Money.of(10000.00);


    public static HourlyEmployee hourlyEmployeeOf(String employeeId, int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        final List<TimeCard> timeCards = createTimeCards(workHours1, workHours2, workHours3, workHours4, workHours5);
        return new HourlyEmployee(employeeId, salaryOfHour, timeCards);
    }

    public static HourlyEmployee hourlyEmployeeOf(String employeeId, List<TimeCard> timeCards) {
        return new HourlyEmployee(employeeId, salaryOfHour, timeCards);
    }

    private static List<TimeCard> createTimeCards(int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        final TimeCard timeCard1 = new TimeCard(LocalDate.of(2019, 11, 4), workHours1);
        final TimeCard timeCard2 = new TimeCard(LocalDate.of(2019, 11, 5), workHours2);
        final TimeCard timeCard3 = new TimeCard(LocalDate.of(2019, 11, 6), workHours3);
        final TimeCard timeCard4 = new TimeCard(LocalDate.of(2019, 11, 7), workHours4);
        final TimeCard timeCard5 = new TimeCard(LocalDate.of(2019, 11, 8), workHours5);

        return asList(timeCard1, timeCard2, timeCard3, timeCard4, timeCard5);
    }

    public static SalariedEmployee salariedEmployeeOf(String employeeId) {
        return new SalariedEmployee(employeeId, salaryOfMonth);
    }

    public static SalariedEmployee salariedEmployeeWithOneSickLeaveOf(String employeeId) {
        Absence sickLeave = new Absence(employeeId, LocalDate.of(2019, 11, 4), LeaveReason.SICK_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, sickLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOneCasualLeaveOf(String employeeId) {
        Absence casualLeave = new Absence(employeeId, LocalDate.of(2019, 11, 4), LeaveReason.CASUAL_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, casualLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOnePaidLeaveOf(String employeeId) {
        Absence paidLeave = new Absence(employeeId, LocalDate.of(2019, 11, 4), LeaveReason.MATERNITY_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, paidLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOneDisapprovedLeaveOf(String employeeId) {
        Absence disapprovedLeave = new Absence(employeeId, LocalDate.of(2019, 11, 4), LeaveReason.DISAPPROVED_LEAVE);

        return createSalariedEmployeeWithAbsences(employeeId, disapprovedLeave);
    }

    private static SalariedEmployee createSalariedEmployeeWithAbsences(String employeeId, Absence... leaves) {
        return new SalariedEmployee(employeeId, salaryOfMonth, new ArrayList<>(asList(leaves)));
    }
}