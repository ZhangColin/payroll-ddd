package payroll.payrollcontext.domain.salariedemployee;


import org.junit.Before;
import org.junit.Test;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.PayrollCalculator;
import payroll.payrollcontext.domain.PayrollCalculatorTest;
import payroll.payrollcontext.domain.Period;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static payroll.fixture.EmployeeFixture.salariedEmployeeOf;
import static payroll.fixture.EmployeeFixture.salariedEmployeeWithOneSickLeaveOf;

public class SalariedEmployeePayrollCalculatorTest extends PayrollCalculatorTest {
    private Period settlementPeriod;
    private SalariedEmployeeRepository mockRepository;
    private List<SalariedEmployee> salariedEmployees;
    private PayrollCalculator calculator;

    @Before
    public void setup() {
        settlementPeriod = new Period(LocalDate.of(2019, 11, 4), LocalDate.of(2019, 11, 8));
        mockRepository = mock(SalariedEmployeeRepository.class);
        salariedEmployees = new ArrayList<>();
        calculator = new SalariedEmployeePayrollCalculator(mockRepository);
    }

    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        // given
        when(mockRepository.allEmployeesOf()).thenReturn(new ArrayList<>());

        // when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        // then
        verify(mockRepository, times(1)).allEmployeesOf();
        assertThat(payrolls).isNotNull().isEmpty();
    }

    @Test
    public void should_calculate_payroll_when_only_one_matched_employee_found() {
        //given
        String employeeId = "emp200901011111";
        SalariedEmployee salariedEmployee = salariedEmployeeOf(employeeId, 10000.00);
        salariedEmployees.add(salariedEmployee);

        when(mockRepository.allEmployeesOf()).thenReturn(salariedEmployees);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepository, times(1)).allEmployeesOf();

        assertThat(payrolls).isNotNull().hasSize(1);
        assertPayroll(employeeId, payrolls, 0, settlementPeriod, 10000.00);
    }

    @Test
    public void should_calculate_payroll_when_more_than_one_matched_employee_found() {
        //given
        String employeeId1 = "emp200901011111";
        SalariedEmployee salariedEmployee1 = salariedEmployeeWithOneSickLeaveOf(employeeId1, 10000.00);
        salariedEmployees.add(salariedEmployee1);

        String employeeId2 = "emp200901011112";
        SalariedEmployee salariedEmployee2 = salariedEmployeeOf(employeeId2, 5000.00);
        salariedEmployees.add(salariedEmployee2);

        String employeeId3 = "emp200901011113";
        SalariedEmployee salariedEmployee3 = salariedEmployeeOf(employeeId3, 8000.00);
        salariedEmployees.add(salariedEmployee3);

        when(mockRepository.allEmployeesOf()).thenReturn(salariedEmployees);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepository, times(1)).allEmployeesOf();

        assertThat(payrolls).isNotNull().hasSize(3);
        assertPayroll(employeeId1, payrolls, 0, settlementPeriod, 9772.73);
        assertPayroll(employeeId2, payrolls, 1, settlementPeriod, 5000.00);
        assertPayroll(employeeId3, payrolls, 2, settlementPeriod, 8000.00);
    }
}