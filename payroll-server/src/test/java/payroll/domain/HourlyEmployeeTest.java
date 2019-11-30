package payroll.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author colin
 */
public class HourlyEmployeeTest {
    private final Period settlementPeriod = new Period(
            LocalDate.of(2019, 11, 4),
            LocalDate.of(2019, 11, 8));
    private final Money salaryOfHour = Money.of(100, Currency.RMB);
    private final String employeeId = "emp200901011111";

    @Test
    public void should_calculate_payroll_by_work_hours_in_a_week() {
        // given
        final List<TimeCard> timeCards = createTimeCards(8, 8, 8, 8, 8);
        final HourlyEmployee hourlyEmployee = new HourlyEmployee(employeeId, timeCards, salaryOfHour);

        // when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        // then
        assertThat(payroll).isNotNull();
        assertThat(payroll.getEmployeeId()).isEqualTo(employeeId);
        assertThat(payroll.getBeginDate()).isEqualTo(LocalDate.of(2019, 11, 4));
        assertThat(payroll.getEndDate()).isEqualTo(LocalDate.of(2019, 11, 8));
        assertThat(payroll.getAmount()).isEqualTo(Money.of(4000, Currency.RMB));
    }

    @Test
    public void should_calculate_payroll_by_work_hours_with_overtime_in_a_week() {
        // given
        final List<TimeCard> timeCards = createTimeCards(9, 7, 10, 10, 8);
        final HourlyEmployee hourlyEmployee = new HourlyEmployee(employeeId, timeCards, salaryOfHour);

        // when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        // then
        assertThat(payroll).isNotNull();
        assertThat(payroll.getEmployeeId()).isEqualTo(employeeId);
        assertThat(payroll.getBeginDate()).isEqualTo(LocalDate.of(2019, 11, 4));
        assertThat(payroll.getEndDate()).isEqualTo(LocalDate.of(2019, 11, 8));
        assertThat(payroll.getAmount()).isEqualTo(Money.of(4650, Currency.RMB));
    }

    @Test
    public void should_be_0_given_no_any_time_card() {
        // given
        final HourlyEmployee hourlyEmployee = new HourlyEmployee(employeeId, new ArrayList<>(), salaryOfHour);

        // when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        // then
        assertThat(payroll).isNotNull();
        assertThat(payroll.getEmployeeId()).isEqualTo(employeeId);
        assertThat(payroll.getAmount()).isEqualTo(Money.zero());
    }

    @Test
    public void should_be_0_given_null_time_card() {
        // given
        final HourlyEmployee hourlyEmployee = new HourlyEmployee(employeeId, null, salaryOfHour);

        // when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        // then
        assertThat(payroll).isNotNull();
        assertThat(payroll.getEmployeeId()).isEqualTo(employeeId);
        assertThat(payroll.getAmount()).isEqualTo(Money.zero());
    }

    private List<TimeCard> createTimeCards(int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        final TimeCard timeCard1 = new TimeCard(LocalDate.of(2019, 11, 4), workHours1);
        final TimeCard timeCard2 = new TimeCard(LocalDate.of(2019, 11, 5), workHours2);
        final TimeCard timeCard3 = new TimeCard(LocalDate.of(2019, 11, 6), workHours3);
        final TimeCard timeCard4 = new TimeCard(LocalDate.of(2019, 11, 7), workHours4);
        final TimeCard timeCard5 = new TimeCard(LocalDate.of(2019, 11, 8), workHours5);

        return asList(timeCard1, timeCard2, timeCard3, timeCard4, timeCard5);
    }
}
