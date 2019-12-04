package payroll.payrollcontext.domain.salariedemployee;

import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.PayrollCalculator;
import payroll.payrollcontext.domain.Period;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class SalariedEmployeePayrollCalculator implements PayrollCalculator {
    private SalariedEmployeeRepository employeeRepository;

    public SalariedEmployeePayrollCalculator(SalariedEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Payroll> execute(Period settlementPeriod) {
        final List<SalariedEmployee> salariedEmployees = employeeRepository.allEmployeesOf();

        return salariedEmployees.stream().map(e->e.payroll(settlementPeriod)).collect(toList());
    }
}
