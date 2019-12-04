package payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Before;
import org.junit.Test;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.PayrollCalculatorTest;
import payroll.payrollcontext.domain.Period;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static payroll.fixture.EmployeeFixture.hourlyEmployeeOf;

public class HourlyEmployeePayrollCalculatorTest extends PayrollCalculatorTest {
    private Period settlementPeriod;
    private HourlyEmployeeRepository mockRepository;
    private HourlyEmployeePayrollCalculator calculator;

    @Before
    public void setUp() throws Exception {
        settlementPeriod = new Period(LocalDate.of(2019, 11, 4),
                LocalDate.of(2019, 11, 8));
        mockRepository = mock(HourlyEmployeeRepository.class);
        calculator = new HourlyEmployeePayrollCalculator(mockRepository);
    }

    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        // given
        when(mockRepository.allEmployeesOf()).thenReturn(new ArrayList<>());

        // when
        final List<Payroll> payrolls = calculator.execute(settlementPeriod);

        // then
        verify(mockRepository, times(1)).allEmployeesOf();
        assertThat(payrolls).isNotNull().isEmpty();
    }

    @Test
    public void should_calculate_payroll_when_only_one_matched_employee_found() {
        // given
        String employeeId = "emp200901011111";
        final HourlyEmployee hourlyEmployee = hourlyEmployeeOf(employeeId, 8, 8, 8, 8, 8);
        final List<HourlyEmployee> hourlyEmployees = singletonList(hourlyEmployee);

        when(mockRepository.allEmployeesOf()).thenReturn(hourlyEmployees);

        // when
        final List<Payroll> payrolls = calculator.execute(settlementPeriod);

        // then
        verify(mockRepository, times(1)).allEmployeesOf();
        assertThat(payrolls).isNotNull().hasSize(1);
        assertPayroll(employeeId, payrolls, 0, settlementPeriod, 4000);
    }

    @Test
    public void should_calculate_payroll_when_more_than_one_matched_employee_found() {
        // given
        String employeeId1 = "emp200901011111";
        final HourlyEmployee hourlyEmployee1 = hourlyEmployeeOf(employeeId1, 8, 8, 8, 8, 8);
        String employeeId2 = "emp200901011112";
        final HourlyEmployee hourlyEmployee2 = hourlyEmployeeOf(employeeId2, 9, 7, 10, 10, 8);
        String employeeId3 = "emp200901011113";
        final HourlyEmployee hourlyEmployee3 = hourlyEmployeeOf(employeeId3, null);
        final List<HourlyEmployee> hourlyEmployees = asList(hourlyEmployee1, hourlyEmployee2, hourlyEmployee3);

        when(mockRepository.allEmployeesOf()).thenReturn(hourlyEmployees);

        // when
        final List<Payroll> payrolls = calculator.execute(settlementPeriod);

        // then
        verify(mockRepository, times(1)).allEmployeesOf();
        assertThat(payrolls).isNotNull().hasSize(3);
        assertPayroll(employeeId1, payrolls, 0, settlementPeriod, 4000);
        assertPayroll(employeeId2, payrolls, 1, settlementPeriod, 4650);
        assertPayroll(employeeId3, payrolls, 2, settlementPeriod, 0);
    }

}