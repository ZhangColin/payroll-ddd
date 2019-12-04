package payroll.payrollcontext.domain;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PayrollCalculatorTest {
    protected void assertPayroll(String employeeId, List<Payroll> payrolls, int index, Period settlementPeriod, double payrollAmount) {
        final Payroll payroll = payrolls.get(index);
        assertThat(payroll.getEmployeeId().value()).isEqualTo(employeeId);
        assertThat(payroll.getBeginDate()).isEqualTo(settlementPeriod.getBeginDate());
        assertThat(payroll.getEndDate()).isEqualTo(settlementPeriod.getEndDate());
        assertThat(payroll.getAmount()).isEqualTo(Salary.of(payrollAmount, Currency.RMB));
    }
}
