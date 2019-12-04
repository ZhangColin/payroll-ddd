package payroll.payrollcontext.domain;

public interface Payrollable {
    Payroll payroll(Period settlementPeriod);
}
