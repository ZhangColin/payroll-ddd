package payroll.fixture;

import payroll.domain.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

public class EmployeeFixture {
    private final static Money salaryOfHour = Money.of(100, Currency.RMB);

    public static HourlyEmployee createHourlyEmployee(String employeeId, int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        final List<TimeCard> timeCards = createTimeCards(workHours1, workHours2, workHours3, workHours4, workHours5);
        return new HourlyEmployee(employeeId, timeCards, salaryOfHour);
    }

    public static HourlyEmployee createHourlyEmployee(String employeeId, List<TimeCard> timeCards) {
        return new HourlyEmployee(employeeId, timeCards, salaryOfHour);
    }

    private static List<TimeCard> createTimeCards(int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        final TimeCard timeCard1 = new TimeCard(LocalDate.of(2019, 11, 4), workHours1);
        final TimeCard timeCard2 = new TimeCard(LocalDate.of(2019, 11, 5), workHours2);
        final TimeCard timeCard3 = new TimeCard(LocalDate.of(2019, 11, 6), workHours3);
        final TimeCard timeCard4 = new TimeCard(LocalDate.of(2019, 11, 7), workHours4);
        final TimeCard timeCard5 = new TimeCard(LocalDate.of(2019, 11, 8), workHours5);

        return asList(timeCard1, timeCard2, timeCard3, timeCard4, timeCard5);
    }
}