package payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static payroll.fixture.EmployeeFixture.createTimeCards;
import static payroll.fixture.EmployeeFixture.hourlyEmployeeOf;

public class SubmitTimeCardTest {
    private final String employeeId = "emp200901011111";

    @Test
    public void should_submit_valid_time_cards() {
        // given
        final HourlyEmployee hourlyEmployee = hourlyEmployeeOf(employeeId, null);
        final List<TimeCard> timeCards = createTimeCards(8, 8, 8, 8, 8);

        assertThat(hourlyEmployee.getTimeCards()).isNotNull().isEmpty();

        // when
        hourlyEmployee.submit(timeCards);

        // then
        assertThat(hourlyEmployee.getTimeCards()).hasSize(5);
    }

    @Test
    public void should_not_submit_time_card_with_same_work_day() {
        // given
        final HourlyEmployee hourlyEmployee = hourlyEmployeeOf(employeeId, 8, 8, 8, 8, 8);
        final List<TimeCard> repeatedTimeCards = createTimeCards(8, 8, 8, 8, 8);

        assertThat(hourlyEmployee.getTimeCards()).hasSize(5);

        // when
        hourlyEmployee.submit(repeatedTimeCards);

        // then
        assertThat(hourlyEmployee.getTimeCards()).hasSize(5);
    }

    @Test
    public void should_submit_a_valid_time_cards() {
        // given
        final HourlyEmployee hourlyEmployee = hourlyEmployeeOf(employeeId, null);
        final TimeCard timeCard = new TimeCard(LocalDate.of(2019, 11, 4), 8);

        assertThat(hourlyEmployee.getTimeCards()).isNotNull().isEmpty();
        // when
        hourlyEmployee.submit(timeCard);

        // then
        assertThat(hourlyEmployee.getTimeCards()).hasSize(1);
    }

    @Test
    public void should_not_submit_time_card_with_same_word_day() {
        // given
         HourlyEmployee hourlyEmployee = hourlyEmployeeOf(employeeId, 8,8,8,8,8);
         TimeCard repeatedCard = new TimeCard(LocalDate.of(2019, 11, 4), 8);
         TimeCard newCard = new TimeCard(LocalDate.of(2019, 11, 11), 8);

        assertThat(hourlyEmployee.getTimeCards()).hasSize(5);

        // when
        hourlyEmployee.submit(repeatedCard);
        hourlyEmployee.submit(newCard);

        // then
        assertThat(hourlyEmployee.getTimeCards()).hasSize(6);
    }
}
