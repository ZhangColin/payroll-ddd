package payroll.payrollcontext.domain.salariedemployee;

import org.junit.Test;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;
import payroll.payrollcontext.domain.Money;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

public class SalariedEmployeeTest {
    private final Period settlementPeriod = new Period(YearMonth.of(2019, 11));
    private final String employeeId = "emp200901011111";
    private final Money salaryOfMonth = Money.of(10000.00);

    @Test
    public void should_return_monthly_salary_if_employee_without_absence() {
        // given
        final SalariedEmployee salariedEmployee = new SalariedEmployee(employeeId, salaryOfMonth);

        // when
        final Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        // then
        assertThat(payroll).isNotNull();
        assertThat(payroll.getEmployeeId()).isEqualTo(employeeId);
        assertThat(payroll.getBeginDate()).isEqualTo(LocalDate.of(2019, 11, 1));
        assertThat(payroll.getEndDate()).isEqualTo(LocalDate.of(2019, 11, 30));
        assertThat(payroll.getAmount()).isEqualTo(salaryOfMonth);
    }
}