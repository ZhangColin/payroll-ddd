package payroll.payrollcontext.domain;

import payroll.payrollcontext.domain.exceptions.NotSameCurrencyException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author colin
 */
@Embeddable
public class Salary {
    public static final int SCALE = 2;

    @Column(name = "salary")
    private BigDecimal value;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public static Salary of(double value) {
        return new Salary(value, Currency.RMB);
    }

    public static Salary of(double value, Currency currency) {
        return new Salary(value, currency);
    }

    protected Salary() {

    }

    private Salary(double value, Currency currency) {
        this.value = BigDecimal.valueOf(value).setScale(SCALE, RoundingMode.DOWN);
        this.currency = currency;
    }

    private Salary(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public static Salary zero() {
        return new Salary(0d, Currency.RMB);
    }

    public Salary add(Salary salary) {
        throwExceptionIfNotSameCurrency(salary);
        return new Salary(value.add(salary.value).setScale(SCALE, RoundingMode.DOWN), currency);
    }

    public Salary subtract(Salary salary) {
        throwExceptionIfNotSameCurrency(salary);
        return new Salary(value.subtract(salary.value).setScale(SCALE, RoundingMode.DOWN), currency);
    }

    public Salary multiply(double factor) {
        return new Salary(value.multiply(BigDecimal.valueOf(factor))
                .setScale(SCALE, RoundingMode.DOWN), currency);
    }

    public Salary divide(double multiplicand) {
        return new Salary(value.divide(BigDecimal.valueOf(multiplicand), SCALE, RoundingMode.DOWN), currency);
    }

    private void throwExceptionIfNotSameCurrency(Salary salary) {
        if (salary.currency != this.currency) {
            throw new NotSameCurrencyException("Don't support different currency.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Salary salary = (Salary) obj;
        return Objects.equals(value, salary.value) &&
                currency == salary.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "Money {" +
                "face value = " + value +
                ", currency = " + currency +
                "}";
    }
}
