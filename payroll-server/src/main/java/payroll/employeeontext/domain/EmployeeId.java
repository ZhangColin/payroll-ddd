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

    protected EmployeeId() {
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
        int length = 6;
        final String number = String.valueOf(random.nextInt(999999));
        if (number.length() == length) {
            return number;
        }
        if (number.length() < length) {
            return appendWithZero(number, length);
        }
        return number.substring(0, length);
    }

    private static String appendWithZero(String str, int length) {
        int numberOfZero = length-str.length();
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberOfZero; i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }
}
