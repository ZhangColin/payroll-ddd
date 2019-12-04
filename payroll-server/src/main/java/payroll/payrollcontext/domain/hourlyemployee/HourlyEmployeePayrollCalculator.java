package payroll.payrollcontext.domain.hourlyemployee;

import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.PayrollCalculator;
import payroll.payrollcontext.domain.Period;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
public class HourlyEmployeePayrollCalculator implements PayrollCalculator {
    private HourlyEmployeeRepository employeeRepository;

    public HourlyEmployeePayrollCalculator(HourlyEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Payroll> execute(Period settlementPeriod) {
        final List<HourlyEmployee> hourlyEmployees = employeeRepository.allEmployeesOf();
        return hourlyEmployees.stream()
                .map(e->e.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}
