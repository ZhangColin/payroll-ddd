package payroll.payrollcontext.domain.salariedemployee;

import payroll.payrollcontext.domain.Money;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;

import java.util.ArrayList;
import java.util.List;

public class SalariedEmployee {
    private static final int WORK_DAYS_OF_MONTH = 22;
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
        final Money salaryOfDay = salaryOfMonth.divide(WORK_DAYS_OF_MONTH);

        final Money deduction = absences.stream()
                .filter(absence -> !absence.isPaidLeave())
                .map(absence -> salaryOfDay.multiply(absence.deductionRation()))
                .reduce(Money.zero(), (m, total) -> total.add(m));
        return new Payroll(employeeId, settlementPeriod.getBeginDate(), settlementPeriod.getEndDate(), salaryOfMonth.subtract(deduction));
    }
}
