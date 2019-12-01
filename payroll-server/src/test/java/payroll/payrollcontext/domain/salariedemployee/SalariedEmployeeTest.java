package payroll.payrollcontext.domain.salariedemployee;

import org.junit.Test;
import payroll.payrollcontext.domain.Money;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;
import static payroll.fixture.EmployeeFixture.*;

public class SalariedEmployeeTest {
    private final Period settlementPeriod = new Period(YearMonth.of(2019, 11));
    private final String employeeId = "emp200901011111";
    private final Money salaryOfMonth = Money.of(10000.00);

    @Test
    public void should_return_monthly_salary_if_employee_without_absence() {
        // given
        final SalariedEmployee salariedEmployee = salariedEmployeeOf(employeeId);

        // when
        final Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        // then
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 11, 1),
                LocalDate.of(2019, 11, 30),
                salaryOfMonth);
    }

    @Test
    public void should_deduct_salary_if_employee_ask_one_day_sick_leave() {
        // given
        final SalariedEmployee salariedEmployee = salariedEmployeeWithOneSickLeaveOf(employeeId);

        // when
        final Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        // then
        final Money expectedAmount = Money.of(9772.73);
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 11, 1),
                LocalDate.of(2019, 11, 30),
                expectedAmount);
    }

    @Test
    public void should_deduct_salary_if_employee_ask_one_day_casual_leave() {
        // given
        final SalariedEmployee salariedEmployee = salariedEmployeeWithOneCasualLeaveOf(employeeId);

        // when
        final Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        // then
        final Money expectedAmount = Money.of(9772.73);
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 11, 1),
                LocalDate.of(2019, 11, 30),
                expectedAmount);
    }

    private void assertPayroll(Payroll payroll, String employeeId, LocalDate beginDate, LocalDate endDate, Money payrollAmount) {
        assertThat(payroll).isNotNull();
        assertThat(payroll.getEmployeeId()).isEqualTo(employeeId);
        assertThat(payroll.getBeginDate()).isEqualTo(beginDate);
        assertThat(payroll.getEndDate()).isEqualTo(endDate);
        assertThat(payroll.getAmount()).isEqualTo(payrollAmount);
    }
}