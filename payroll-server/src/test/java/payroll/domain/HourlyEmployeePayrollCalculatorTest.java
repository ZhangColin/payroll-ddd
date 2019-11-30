package payroll.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static payroll.fixture.EmployeeFixture.createHourlyEmployee;

public class HourlyEmployeePayrollCalculatorTest {

    private Period settlementPeriod;
    private HourlyEmployeeRepository mockRepository;

    @Before
    public void setUp() throws Exception {
        settlementPeriod = new Period(LocalDate.of(2019, 11, 4),
                LocalDate.of(2019, 11, 8));
        mockRepository = mock(HourlyEmployeeRepository.class);
    }

    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        // given
        when(mockRepository.allEmployeesOf(settlementPeriod)).thenReturn(new ArrayList<>());

        final HourlyEmployeePayrollCalculator calculator = new HourlyEmployeePayrollCalculator();
        calculator.setRepository(mockRepository);

        // when
        final List<Payroll> payrolls = calculator.execute(settlementPeriod);

        // then
        verify(mockRepository, times(1)).allEmployeesOf(settlementPeriod);
        assertThat(payrolls).isNotNull().isEmpty();
    }

    @Test
    public void should_calculate_payroll_when_only_one_matched_employee_found() {
        // given
        String employeeId = "emp200901011111";
        final HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, 8, 8, 8, 8, 8);
        final List<HourlyEmployee> hourlyEmployees = asList(hourlyEmployee);

        when(mockRepository.allEmployeesOf(settlementPeriod)).thenReturn(hourlyEmployees);

        final HourlyEmployeePayrollCalculator calculator = new HourlyEmployeePayrollCalculator();
        calculator.setRepository(mockRepository);

        // when
        final List<Payroll> payrolls = calculator.execute(settlementPeriod);

        // then
        verify(mockRepository, times(1)).allEmployeesOf(settlementPeriod);
        assertThat(payrolls).isNotNull().hasSize(1);

        final Payroll payroll = payrolls.get(0);
        assertThat(payroll.getEmployeeId()).isEqualTo(employeeId);
        assertThat(payroll.getBeginDate()).isEqualTo(LocalDate.of(2019, 11, 4));
        assertThat(payroll.getEndDate()).isEqualTo(LocalDate.of(2019, 11, 8));
        assertThat(payroll.getAmount()).isEqualTo(Money.of(4000, Currency.RMB));
    }
}