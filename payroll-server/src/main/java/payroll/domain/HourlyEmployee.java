package payroll.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @author colin
 */
public class HourlyEmployee {
    private final List<TimeCard> timeCards;
    private final Money salaryOfHour;

    public HourlyEmployee(List<TimeCard> timeCards, Money salaryOfHour) {
        this.timeCards = timeCards;
        this.salaryOfHour = salaryOfHour;
    }

    public Payroll payroll() {
        final int totalHours = timeCards.stream()
                .map(TimeCard::getWorkHours)
                .reduce(0, (hours, total) -> hours + total);

        final Period settlementPeriod = settlementPeriod();

        return new Payroll(
                settlementPeriod.beginDate,
                settlementPeriod.endDate,
                salaryOfHour.multiply(totalHours));
    }

    private Period settlementPeriod() {
        Collections.sort(timeCards);

        final LocalDate beginDate = timeCards.get(0).getWorkDay();
        final LocalDate endDate = timeCards.get(timeCards.size() - 1).getWorkDay();
        return new Period(beginDate, endDate);
    }

    private class Period {
        private final LocalDate beginDate;
        private final LocalDate endDate;

        public Period(LocalDate beginDate, LocalDate endDate) {
            this.beginDate = beginDate;
            this.endDate = endDate;
        }
    }
}
