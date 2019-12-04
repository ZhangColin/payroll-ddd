package payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Test;
import payroll.employeeontext.domain.EmployeeId;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static payroll.fixture.EmployeeFixture.hourlyEmployeeOf;

public class TimeCardServiceTest {
    private String employeeId = "emp200901011111";

    @Test
    public void should_submit_time_card() {
        // given
        final TimeCardService timeCardService = new TimeCardService();

        final EmployeeId employeeIdObj = EmployeeId.of(this.employeeId);

        final HourlyEmployeeRepository mockRepository = mock(HourlyEmployeeRepository.class);
        final HourlyEmployee hourlyEmployee = hourlyEmployeeOf(employeeId, null);
        when(mockRepository.employeeOf(employeeIdObj)).thenReturn(Optional.of(hourlyEmployee));

        timeCardService.setEmployeeRepository(mockRepository);

        final TimeCard newTimeCard = new TimeCard(LocalDate.of(2019, 10, 8), 8);

        // when
        timeCardService.submitTimeCard(employeeIdObj, newTimeCard);

        // then
        verify(mockRepository).employeeOf(employeeIdObj);
        verify(mockRepository).save(hourlyEmployee);
        assertThat(hourlyEmployee.getTimeCards()).hasSize(1);
    }
}