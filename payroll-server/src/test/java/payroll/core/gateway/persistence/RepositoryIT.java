package payroll.core.gateway.persistence;

import org.junit.Test;
import payroll.employeeontext.domain.Address;
import payroll.employeeontext.domain.Contact;
import payroll.employeeontext.domain.Employee;
import payroll.employeeontext.domain.EmployeeId;
import payroll.payrollcontext.domain.Salary;
import payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import payroll.payrollcontext.domain.hourlyemployee.TimeCard;
import payroll.payrollcontext.domain.salariedemployee.Absence;
import payroll.payrollcontext.domain.salariedemployee.SalariedEmployee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class RepositoryIT {
    @Test
    public void try_run_integration_test_only() {
        // given
        final String employeeId = "emp200109101000001";
        final Repository<Employee, EmployeeId> employeeIdRepository =
                new Repository<>(Employee.class, EntityManagers.from("PAYROLL_JPA"));

        // when
        final Optional<Employee> optionalEmployee = employeeIdRepository.findById(EmployeeId.of(employeeId));

        // then
        assertThat(optionalEmployee.isPresent()).isTrue();

        final Employee employee = optionalEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.name()).isEqualTo("Bruce");
        assertThat(employee.email().value()).isEqualTo("bruce@payroll.com");
        assertThat(employee.isHourly()).isTrue();
        assertThat(employee.isMale()).isTrue();
        assertThat(employee.address()).isEqualTo(
                new Address("China", "SiChuan", "chengdu", "qingyang avenue", "600000"));
        assertThat(employee.contact()).isEqualTo(Contact.of("15028150000"));
        assertThat(employee.contact().getHomePhone()).isNull();
        assertThat(employee.boardingDate()).isEqualTo(LocalDate.of(2001, 9, 10));
    }

    @Test
    public void should_query_hourly_employee_and_related_timecards_by_id() {
        // given
        final String employeeId = "emp200109101000001";
        final Repository<HourlyEmployee, EmployeeId> employeeIdRepository =
                new Repository<>(HourlyEmployee.class, EntityManagers.from("PAYROLL_JPA"));

        // when
        final Optional<HourlyEmployee> optionalEmployee = employeeIdRepository.findById(EmployeeId.of(employeeId));

        // then
        assertThat(optionalEmployee.isPresent()).isTrue();

        final HourlyEmployee employee = optionalEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.getSalaryOfHour()).isEqualTo(Salary.of(100));

        final List<TimeCard> timeCards = employee.getTimeCards();
        assertThat(timeCards).isNotNull().hasSize(5);

        final TimeCard timeCard = timeCards.get(0);
        assertThat(timeCard.getWorkHours()).isEqualTo(8);
        assertThat(timeCard.getRegularWorkHours()).isEqualTo(8);
        assertThat(timeCard.getOvertimeWorkHours()).isEqualTo(0);
        assertThat(timeCard.isOvertime()).isFalse();
    }

    @Test
    public void should_query_salaried_employee_and_related_absences_by_id() {
        // given
        final String employeeId = "emp201110101000003";
        final Repository<SalariedEmployee, EmployeeId> employeeIdRepository =
                new Repository<>(SalariedEmployee.class, EntityManagers.from("PAYROLL_JPA"));

        // when
        final Optional<SalariedEmployee> optionalEmployee = employeeIdRepository.findById(EmployeeId.of(employeeId));

        // then
        assertThat(optionalEmployee.isPresent()).isTrue();

        final SalariedEmployee employee = optionalEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.getSalaryOfMonth()).isEqualTo(Salary.of(10000.00));

        final List<Absence> absences = employee.getAbsences();
        assertThat(absences).isNotNull().hasSize(4);

        final Absence absence = absences.get(0);
        assertThat(absence.isPaidLeave()).isFalse();
    }
}