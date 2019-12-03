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
    private final Salary amount;

    public Payroll(EmployeeId employeeId, LocalDate beginDate, LocalDate endDate, Salary amount) {
        this.employeeId = employeeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.amount = amount;
    }
}
