package payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Test;

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
}
