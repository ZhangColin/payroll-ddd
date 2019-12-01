package payroll.payrollcontext.domain;

import lombok.Getter;

import java.time.LocalDate;

/**
 * @author colin
 */
@Getter
public class Period {
    private final LocalDate beginDate;
    private final LocalDate endDate;

    public Period(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }
}
