package payroll.domain;

import lombok.Getter;

import java.time.LocalDate;

/**
 * @author colin
 */
@Getter
public class Payroll {
    private final LocalDate beginDate;
    private final LocalDate endDate;
    private final Money amount;

    public Payroll(LocalDate beginDate, LocalDate endDate, Money amount) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.amount = amount;
    }
}
