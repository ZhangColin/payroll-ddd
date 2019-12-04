package payroll.payrollcontext.gateway.persistence;


import org.junit.Before;
import org.junit.Test;
import payroll.core.gateway.persistence.Repository;
import payroll.employeeontext.domain.EmployeeId;
import payroll.fixture.EntityManagerFixture;
import payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import payroll.payrollcontext.domain.hourlyemployee.TimeCard;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class HourlyEmployeeJpaRepositoryIT {
    private EntityManager entityManager;
    private Repository<HourlyEmployee, EmployeeId> repository;
    private HourlyEmployeeJpaRepository employeeRepository;

    @Before
    public void setUp() {
        entityManager = EntityManagerFixture.createEntityManager();
        repository = new Repository<>(HourlyEmployee.class, entityManager);
        employeeRepository = new HourlyEmployeeJpaRepository(repository);
    }

    @Test
    public void should_submit_timecard_then_remove_it() {
        final EmployeeId employeeId = EmployeeId.of("emp200109101000001");

        HourlyEmployee hourlyEmployee = employeeRepository.employeeOf(employeeId).get();

        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.getTimeCards()).hasSize(5);

        TimeCard repeatedCard = new TimeCard(LocalDate.of(2019, 11, 4), 8);
        hourlyEmployee.submit(repeatedCard);
        employeeRepository.save(hourlyEmployee);

        hourlyEmployee = employeeRepository.employeeOf(employeeId).get();
        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.getTimeCards()).hasSize(5);

        TimeCard submittedCard = new TimeCard(LocalDate.of(2019, 11, 10), 8);
        hourlyEmployee.submit(submittedCard);
        employeeRepository.save(hourlyEmployee);

        hourlyEmployee = employeeRepository.employeeOf(employeeId).get();
        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.getTimeCards()).hasSize(6);

        hourlyEmployee.remove(submittedCard);
        employeeRepository.save(hourlyEmployee);

        hourlyEmployee = employeeRepository.employeeOf(employeeId).get();
        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.getTimeCards()).hasSize(5);
    }
}