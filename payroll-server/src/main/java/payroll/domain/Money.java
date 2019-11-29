package payroll.domain;

import lombok.EqualsAndHashCode;

/**
 * @author colin
 */
@EqualsAndHashCode
public class Money {
    private final long value;
    private final Currency currency;

    private Money(long value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money multiply(int factor) {
        return new Money(value * factor, currency);
    }

    public static Money of(long value, Currency currency) {
        return new Money(value, currency);
    }
}
