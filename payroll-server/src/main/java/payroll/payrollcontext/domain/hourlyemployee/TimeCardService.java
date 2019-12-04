package payroll.payrollcontext.domain.hourlyemployee;

import payroll.employeeontext.domain.EmployeeId;

public class TimeCardService {
    private HourlyEmployeeRepository hourlyEmployeeRepository;

    public void setHourlyEmployeeRepository(HourlyEmployeeRepository hourlyEmployeeRepository) {
        this.hourlyEmployeeRepository = hourlyEmployeeRepository;
    }

    public void submitTimeCard(EmployeeId employeeId, TimeCard submitted) {

    }
}
