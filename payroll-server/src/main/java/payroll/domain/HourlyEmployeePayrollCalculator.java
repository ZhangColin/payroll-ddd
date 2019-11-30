package payroll.domain;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
public class HourlyEmployeePayrollCalculator {
    private HourlyEmployeeRepository employeeRepository;

    public void setRepository(HourlyEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Payroll> execute(Period settlementPeriod) {
        final List<HourlyEmployee> hourlyEmployees = employeeRepository.allEmployeesOf(settlementPeriod);
        return hourlyEmployees.stream()
                .map(e->e.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}
