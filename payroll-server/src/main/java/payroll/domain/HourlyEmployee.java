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
                .map(timeCard -> timeCard.getWorkHours() > 8 ? 8 : timeCard.getWorkHours())
                .reduce(0, Integer::sum);

        final int overtimeHours = timeCards.stream()
                .filter(timeCard -> timeCard.getWorkHours() > 8)
                .map(timeCard -> timeCard.getWorkHours() - 8)
                .reduce(0, Integer::sum);

        final Period settlementPeriod = settlementPeriod();

        final Money regularSalary = salaryOfHour.multiply(totalHours);
        final Money overtimeSalary = salaryOfHour.multiply(1.5).multiply(overtimeHours);
        final Money totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(
                settlementPeriod.beginDate,
                settlementPeriod.endDate,
                totalSalary);
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
