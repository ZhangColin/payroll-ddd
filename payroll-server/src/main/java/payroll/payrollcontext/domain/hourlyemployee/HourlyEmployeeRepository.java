package payroll.payrollcontext.domain.hourlyemployee;

import payroll.employeeontext.domain.EmployeeId;

import java.util.List;
import java.util.Optional;

public interface HourlyEmployeeRepository {
    Optional<HourlyEmployee> employeeOf(EmployeeId employeeId);
    List<HourlyEmployee> allEmployeesOf();

    void save(HourlyEmployee employee);
}
