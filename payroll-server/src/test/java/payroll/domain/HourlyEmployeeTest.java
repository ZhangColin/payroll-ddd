package payroll.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static payroll.fixture.EmployeeFixture.createHourlyEmployee;

/**
 * @author colin
 */
public class HourlyEmployeeTest {
    private final Period settlementPeriod = new Period(
            LocalDate.of(2019, 11, 4),
            LocalDate.of(2019, 11, 8));
    private final String employeeId = "emp200901011111";

    @Test
    public void should_calculate_payroll_by_work_hours_in_a_week() {
        // given
        final HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, 8, 8, 8, 8, 8);

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
        final HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, 9, 7, 10, 10, 8);

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
        final HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, new ArrayList<>());

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
        final HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, null);

        // when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        // then
        assertThat(payroll).isNotNull();
        assertThat(payroll.getEmployeeId()).isEqualTo(employeeId);
        assertThat(payroll.getAmount()).isEqualTo(Money.zero());
    }

}
