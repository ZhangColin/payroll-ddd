package payroll.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HourlyEmployeePayrollCalculatorTest {
    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        // given
        final Period settlementPeriod = new Period(LocalDate.of(2019, 11, 4),
                LocalDate.of(2019, 11, 8));

        final HourlyEmployeeRepository mockRepository = mock(HourlyEmployeeRepository.class);
        when(mockRepository.allEmployeesOf(settlementPeriod)).thenReturn(new ArrayList<>());

        final HourlyEmployeePayrollCalculator calculator = new HourlyEmployeePayrollCalculator();
        calculator.setRepository(mockRepository);

        // when
        final List<Payroll> payrolls = calculator.execute(settlementPeriod);

        // then
        verify(mockRepository, times(1)).allEmployeesOf(settlementPeriod);
        assertThat(payrolls).isNotNull().isEmpty();
    }
}