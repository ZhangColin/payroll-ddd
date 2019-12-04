package payroll.payrollcontext.gateway.persistence;

import payroll.core.gateway.persistence.Repository;
import payroll.employeeontext.domain.EmployeeId;
import payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import payroll.payrollcontext.domain.hourlyemployee.HourlyEmployeeRepository;

import java.util.List;
import java.util.Optional;

public class HourlyEmployeeJpaRepository implements HourlyEmployeeRepository {
    private Repository<HourlyEmployee, EmployeeId> repository;

    public HourlyEmployeeJpaRepository(Repository<HourlyEmployee, EmployeeId> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<HourlyEmployee> employeeOf(EmployeeId employeeId) {
        return repository.findById(employeeId);
    }

    @Override
    public List<HourlyEmployee> allEmployeesOf() {
        return repository.findAll();
    }

    @Override
    public void save(HourlyEmployee employee) {
        if (employee == null) {
            return;
        }
        repository.saveOrUpdate(employee);
    }
}
