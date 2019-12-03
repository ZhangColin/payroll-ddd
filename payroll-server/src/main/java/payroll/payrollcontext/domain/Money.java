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
public class Money {
    public static final int SCALE = 2;

    @Column(name = "salary")
    private BigDecimal value;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public static Money of(double value) {
        return new Money(value, Currency.RMB);
    }

    public static Money of(double value, Currency currency) {
        return new Money(value, currency);
    }

    private Money() {

    }

    private Money(double value, Currency currency) {
        this.value = BigDecimal.valueOf(value).setScale(SCALE, RoundingMode.DOWN);
        this.currency = currency;
    }

    private Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public static Money zero() {
        return new Money(0d, Currency.RMB);
    }

    public Money add(Money money) {
        throwExceptionIfNotSameCurrency(money);
        return new Money(value.add(money.value).setScale(SCALE, RoundingMode.DOWN), currency);
    }

    public Money subtract(Money money) {
        throwExceptionIfNotSameCurrency(money);
        return new Money(value.subtract(money.value).setScale(SCALE, RoundingMode.DOWN), currency);
    }

    public Money multiply(double factor) {
        return new Money(value.multiply(BigDecimal.valueOf(factor))
                .setScale(SCALE, RoundingMode.DOWN), currency);
    }

    public Money divide(double multiplicand) {
        return new Money(value.divide(BigDecimal.valueOf(multiplicand), SCALE, RoundingMode.DOWN), currency);
    }

    private void throwExceptionIfNotSameCurrency(Money money) {
        if (money.currency != this.currency) {
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

        Money money = (Money) obj;
        return Objects.equals(value, money.value) &&
                currency == money.currency;
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
