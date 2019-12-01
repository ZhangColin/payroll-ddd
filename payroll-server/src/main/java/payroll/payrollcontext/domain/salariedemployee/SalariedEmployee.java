package payroll.payrollcontext.domain.salariedemployee;

import payroll.payrollcontext.domain.Money;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;

public class SalariedEmployee {
    private String employeeId;
    private Money salaryOfMonth;

    public SalariedEmployee(String employeeId, Money salaryOfMonth) {
        this.employeeId = employeeId;
        this.salaryOfMonth = salaryOfMonth;
    }

    public Payroll payroll(Period settlementPeriod) {
        return null;
    }
}
