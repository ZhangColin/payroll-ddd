package payroll.core.gateway.persistence;

import org.junit.Test;
import payroll.employeeontext.domain.Address;
import payroll.employeeontext.domain.Contact;
import payroll.employeeontext.domain.Employee;
import payroll.employeeontext.domain.EmployeeId;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class RepositoryIT {
    @Test
    public void try_run_integration_test_only() {
        // given
        final Repository<Employee, EmployeeId> employeeIdRepository =
                new Repository<>(Employee.class, EntityManagers.from("PAYROLL_JPA"));

        // when
        final Optional<Employee> optionalEmployee = employeeIdRepository.findById(EmployeeId.of("emp200190101000001"));

        // then
        assertThat(optionalEmployee.isPresent()).isTrue();

        final Employee employee = optionalEmployee.get();
        assertThat(employee.id().value()).isEqualTo("emp200190101000001");
        assertThat(employee.name()).isEqualTo("Bruce");
        assertThat(employee.email().value()).isEqualTo("bruce@payroll.com");
        assertThat(employee.isHourly()).isTrue();
        assertThat(employee.isMale()).isTrue();
        assertThat(employee.address()).isEqualTo(
                new Address("China", "SiChuan", "chengdu", "qingyang avenue", "600000"));
        assertThat(employee.contact()).isEqualTo(Contact.of("15028150000"));
        assertThat(employee.contact().getHomePhone()).isNull();
    }
}