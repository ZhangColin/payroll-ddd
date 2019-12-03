package payroll.payrollcontext.domain;

import lombok.Getter;
import payroll.payrollcontext.domain.exceptions.InvalidDateException;

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
        this(month.getYear(), month.getMonthValue());
    }

    public Period(int year, int month) {
        if (month < 1 || month > 12) {
            throw new InvalidDateException("Invalid month value.");
        }

        this.beginDate = LocalDate.of(year, month, 1);
        this.endDate = LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth());
    }

    public boolean contains(LocalDate date) {
        if (date.isEqual(beginDate) || date.isEqual(endDate)) {
            return true;
        }

        return date.isAfter(beginDate) && date.isBefore(endDate);
    }
}
