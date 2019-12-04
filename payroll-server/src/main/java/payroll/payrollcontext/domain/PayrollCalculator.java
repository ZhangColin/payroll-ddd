package payroll.payrollcontext.domain;

import java.util.List;

public interface PayrollCalculator {
    List<Payroll> execute(Period settlementPeriod);
}
