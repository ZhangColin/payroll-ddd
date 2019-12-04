package payroll.payrollcontext.domain.hourlyemployee;

import payroll.employeeontext.domain.EmployeeId;

import java.util.Optional;

public class TimeCardService {
    private HourlyEmployeeRepository employeeRepository;

    public void setEmployeeRepository(HourlyEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void submitTimeCard(EmployeeId employeeId, TimeCard submitted) {
        final Optional<HourlyEmployee> optionalHourlyEmployee = employeeRepository.employeeOf(employeeId);
        optionalHourlyEmployee.ifPresent(hourlyEmployee->{
            hourlyEmployee.submit(submitted);
            employeeRepository.save(hourlyEmployee);
        });
    }
}
