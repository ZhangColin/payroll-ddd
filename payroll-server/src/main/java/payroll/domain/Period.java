package payroll.domain;

import lombok.Getter;

import java.time.LocalDate;

/**
 * @author colin
 */
@Getter
class Period {
    private final LocalDate beginDate;
    private final LocalDate endDate;

    public Period(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }
}
