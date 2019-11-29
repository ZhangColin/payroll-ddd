package payroll.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author colin
 */
public class HourlyEmployeeTest {
    @Test
    public void should_calculate_payroll_by_work_hours_in_a_week() {
        // given
        final TimeCard timeCard1 = new TimeCard(LocalDate.of(2019, 11, 4), 8);
        final TimeCard timeCard2 = new TimeCard(LocalDate.of(2019, 11, 5), 8);
        final TimeCard timeCard3 = new TimeCard(LocalDate.of(2019, 11, 6), 8);
        final TimeCard timeCard4 = new TimeCard(LocalDate.of(2019, 11, 7), 8);
        final TimeCard timeCard5 = new TimeCard(LocalDate.of(2019, 11, 8), 8);

        final List<TimeCard> timeCards = asList(timeCard1, timeCard2, timeCard3, timeCard4, timeCard5);

        final HourlyEmployee hourlyEmployee = new HourlyEmployee(timeCards, Money.of(10000, Currency.RMB));

        // when
        Payroll payroll = hourlyEmployee.payroll();

        // then
        assertThat(payroll).isNotNull();
        assertThat(payroll.getBeginDate()).isEqualTo(LocalDate.of(2019, 11, 4));
        assertThat(payroll.getEndDate()).isEqualTo(LocalDate.of(2019, 11, 8));
        assertThat(payroll.getAmount()).isEqualTo(Money.of(400000, Currency.RMB));
    }
}
