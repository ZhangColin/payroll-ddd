package payroll.payrollcontext.domain.hourlyemployee;

import payroll.payrollcontext.domain.Period;

import java.util.List;

public interface HourlyEmployeeRepository {
    List<HourlyEmployee> allEmployeesOf(Period settlementPeriod);
}
