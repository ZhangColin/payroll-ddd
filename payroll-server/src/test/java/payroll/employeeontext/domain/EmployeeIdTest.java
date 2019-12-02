package payroll.employeeontext.domain;

import org.junit.Test;
import payroll.core.domain.Identity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeIdTest {
    @Test
    public void should_generate_unique_employee_id_following_rule() {
        Identity<String> nextEmployeeId = EmployeeId.next();

        assertThat(nextEmployeeId.value())
                .startsWith("emp")
                .contains(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .hasSize(17);
    }
}