package payroll.payrollcontext.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.YearMonth;

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

    public Period(YearMonth month) {
        this.beginDate = month.atDay(1);
        this.endDate = month.atDay(30);
    }
}
