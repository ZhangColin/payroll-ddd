package payroll.payrollcontext.domain.salariedemployee;

import payroll.payrollcontext.domain.Money;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;

import java.util.ArrayList;
import java.util.List;

public class SalariedEmployee {
    private String employeeId;
    private Money salaryOfMonth;
    private List<Absence> absences;

    public SalariedEmployee(String employeeId, Money salaryOfMonth) {
        this(employeeId, salaryOfMonth, new ArrayList<>());
    }
    public SalariedEmployee(String employeeId, Money salaryOfMonth, List<Absence> absences) {
        this.employeeId = employeeId;
        this.salaryOfMonth = salaryOfMonth;
        this.absences = absences;
    }

    public Payroll payroll(Period settlementPeriod) {
        return new Payroll(employeeId, settlementPeriod.getBeginDate(), settlementPeriod.getEndDate(), salaryOfMonth);
    }
}
