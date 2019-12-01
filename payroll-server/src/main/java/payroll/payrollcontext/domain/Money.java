package payroll.payrollcontext.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author colin
 */
public class Money {
    public static final int SCALE = 2;
    private final MathContext mathContext = new MathContext(SCALE, RoundingMode.HALF_UP);

    private final BigDecimal value;
    private final Currency currency;

    private Money(double value, Currency currency) {
        this.value = BigDecimal.valueOf(value).round(mathContext);
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
        return new Money(value.add(money.value).round(mathContext), currency);
    }

    public Money multiply(double factor) {
        final BigDecimal factorDecimal = BigDecimal.valueOf(factor).round(mathContext);
        return new Money(value.multiply(factorDecimal).round(mathContext), currency);
    }

    public static Money of(double value) {
        return new Money(value, Currency.RMB);
    }
    public static Money of(double value, Currency currency) {
        return new Money(value, currency);
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
