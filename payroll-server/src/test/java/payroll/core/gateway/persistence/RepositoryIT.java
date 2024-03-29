package payroll.core.gateway.persistence;

import org.junit.Before;
import org.junit.Test;
import payroll.employeeontext.domain.*;
import payroll.fixture.EntityManagerFixture;
import payroll.payrollcontext.domain.Salary;
import payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import payroll.payrollcontext.domain.hourlyemployee.TimeCard;
import payroll.payrollcontext.domain.salariedemployee.Absence;
import payroll.payrollcontext.domain.salariedemployee.SalariedEmployee;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static payroll.fixture.EmployeeFixture.employeeOf;


public class RepositoryIT {
    private EntityManager entityManager;

    @Before
    public void setUp() {
        entityManager = EntityManagerFixture.createEntityManager();
    }

    @Test
    public void try_run_integration_test_only() {
        // given
        final String employeeId = "emp200109101000001";
        final Repository<Employee, EmployeeId> employeeRepository = createEmployeeRepository();

        // when
        final Optional<Employee> optionalEmployee = employeeRepository.findById(EmployeeId.of(employeeId));

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
        final Repository<HourlyEmployee, EmployeeId> employeeRepository = createHourlyEmployeeRepository();

        // when
        final Optional<HourlyEmployee> optionalEmployee = employeeRepository.findById(EmployeeId.of(employeeId));

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
        final Repository<SalariedEmployee, EmployeeId> employeeRepository = createSalariedEmployeeRepository();

        // when
        final Optional<SalariedEmployee> optionalEmployee = employeeRepository.findById(EmployeeId.of(employeeId));

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

    @Test
    public void should_get_all_employees() {
        // given
        final Repository<Employee, EmployeeId> employeeRepository = createEmployeeRepository();

        // when
        final List<Employee> employees = employeeRepository.findAll();

        // then
        assertThat(employees).isNotNull().hasSize(5);
    }

    @Test
    public void should_get_all_hourly_employees() {
        // given
        final Repository<HourlyEmployee, EmployeeId> employeeRepository = createHourlyEmployeeRepository();

        // when
        final List<HourlyEmployee> employees = employeeRepository.findAll();

        // then
        assertThat(employees).isNotNull().hasSize(2);
    }

    @Test
    public void should_get_all_salaried_employees() {
        // given
        final Repository<SalariedEmployee, EmployeeId> employeeRepository = createSalariedEmployeeRepository();

        // when
        final List<SalariedEmployee> employees = employeeRepository.findAll();

        // then
        assertThat(employees).isNotNull().hasSize(1);
    }

    @Test
    public void should_get_all_entities_by_employee_type() {
        final Repository<Employee, EmployeeId> employeeRepository = createEmployeeRepository();

        final List<Employee> hourlyEmployees = employeeRepository
                .findBy((builder, query, root) -> builder.equal(root.get("employeeType"), EmployeeType.HOURLY));

        assertThat(hourlyEmployees).isNotNull().hasSize(2);
    }

    @Test
    public void should_add_new_employee_then_remove_it() {
        final Repository<Employee, EmployeeId> employeeRepository = createEmployeeRepository();
        final Employee employee = employeeOf("emp200109101000099", "test", "test@payroll.com", EmployeeType.HOURLY);

        employeeRepository.saveOrUpdate(employee);

        Optional<Employee> foundEmployee = employeeRepository.findById(EmployeeId.of("emp200109101000099"));
        assertThat(foundEmployee.isPresent()).isTrue();

        employeeRepository.delete(employee);
        foundEmployee = employeeRepository.findById(EmployeeId.of("emp200109101000099"));
        assertThat(foundEmployee.isPresent()).isFalse();
    }

    private Repository<SalariedEmployee, EmployeeId> createSalariedEmployeeRepository() {
        return new Repository<>(SalariedEmployee.class, entityManager);
    }

    private Repository<HourlyEmployee, EmployeeId> createHourlyEmployeeRepository() {
        return new Repository<>(HourlyEmployee.class, entityManager);
    }

    private Repository<Employee, EmployeeId> createEmployeeRepository() {
        return new Repository<>(Employee.class, entityManager);
    }
}