package payroll.employeeontext.domain;

import lombok.EqualsAndHashCode;
import payroll.core.domain.Identity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author colin
 */
@EqualsAndHashCode
@Embeddable
public class EmployeeId implements Identity<String>, Serializable {
    @Column(name="id")
    private String value;

    private static Random random;

    static {
        random = new Random();
    }

    private EmployeeId() {
    }

    private EmployeeId(String value) {

        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static EmployeeId of(String value) {
        return new EmployeeId(value);
    }

    public static Identity<String> next() {
        return new EmployeeId(String.format("%s%s%s",
                COMPOSE_PREFIX,
                composeTimestamp(),
                composeRandomNumber()));
    }

    private static final String COMPOSE_PREFIX ="emp";


    private static String composeTimestamp() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private static String composeRandomNumber() {
        return String.valueOf(random.nextInt(999999));
    }
}
