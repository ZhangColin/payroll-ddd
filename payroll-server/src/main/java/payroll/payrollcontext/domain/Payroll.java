package payroll.payrollcontext.domain;

import lombok.Getter;
import payroll.employeeontext.domain.EmployeeId;

import java.time.LocalDate;

/**
 * @author colin
 */
@Getter
public class Payroll {
    private final EmployeeId employeeId;
    private final LocalDate beginDate;
    private final LocalDate endDate;
    private final Money amount;

    public Payroll(EmployeeId employeeId, LocalDate beginDate, LocalDate endDate, Money amount) {
        this.employeeId = employeeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.amount = amount;
    }
}
