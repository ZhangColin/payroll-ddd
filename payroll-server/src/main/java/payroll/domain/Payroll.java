package payroll.domain;

import lombok.Getter;

import java.time.LocalDate;

/**
 * @author colin
 */
@Getter
public class Payroll {
    private final String employeeId;
    private final LocalDate beginDate;
    private final LocalDate endDate;
    private final Money amount;

    public Payroll(String employeeId, LocalDate beginDate, LocalDate endDate, Money amount) {
        this.employeeId = employeeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.amount = amount;
    }
}
