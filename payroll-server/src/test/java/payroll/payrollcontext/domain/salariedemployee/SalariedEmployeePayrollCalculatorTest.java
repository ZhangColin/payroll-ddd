package payroll.payrollcontext.domain.salariedemployee;


import org.junit.Before;
import org.junit.Test;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SalariedEmployeePayrollCalculatorTest {
    private Period settlementPeriod;
    private SalariedEmployeeRepository mockRepository;

    @Before
    public void setup() {
        settlementPeriod = new Period(LocalDate.of(2019, 11, 4), LocalDate.of(2019, 11, 8));
        mockRepository = mock(SalariedEmployeeRepository.class);
    }

    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        // given
        when(mockRepository.allEmployeesOf()).thenReturn(new ArrayList<>());

        final SalariedEmployeePayrollCalculator salariedEmployeePayrollCalculator = new SalariedEmployeePayrollCalculator();
        salariedEmployeePayrollCalculator.setEmployeeRepository(mockRepository);

        // when
        List<Payroll> payrolls = salariedEmployeePayrollCalculator.execute(settlementPeriod);

        // then
        verify(mockRepository, times(1)).allEmployeesOf();
        assertThat(payrolls).isNotNull().isEmpty();
    }
}