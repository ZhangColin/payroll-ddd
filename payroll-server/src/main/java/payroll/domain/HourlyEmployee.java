package payroll.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @author colin
 */
public class HourlyEmployee {
    public static final int MAXIMUM_REGULAR_HOURS = 8;
    public static final double OVERTIME_FACTOR = 1.5;
    private final List<TimeCard> timeCards;
    private final Money salaryOfHour;

    public HourlyEmployee(List<TimeCard> timeCards, Money salaryOfHour) {
        this.timeCards = timeCards;
        this.salaryOfHour = salaryOfHour;
    }

    public Payroll payroll() {
        final int totalHours = timeCards.stream()
                .map(this::getRegularWorkHours)
                .reduce(0, Integer::sum);

        final int overtimeHours = timeCards.stream()
                .filter(this::isOvertime)
                .map(this::getOvertimeWorkHours)
                .reduce(0, Integer::sum);

        final Period settlementPeriod = settlementPeriod();

        final Money regularSalary = salaryOfHour.multiply(totalHours);
        final Money overtimeSalary = salaryOfHour.multiply(OVERTIME_FACTOR).multiply(overtimeHours);
        final Money totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(
                settlementPeriod.beginDate,
                settlementPeriod.endDate,
                totalSalary);
    }

    private int getOvertimeWorkHours(TimeCard timeCard) {
        return timeCard.getWorkHours() - MAXIMUM_REGULAR_HOURS;
    }

    private boolean isOvertime(TimeCard timeCard) {
        return timeCard.getWorkHours() > MAXIMUM_REGULAR_HOURS;
    }

    private int getRegularWorkHours(TimeCard timeCard) {
        return isOvertime(timeCard) ? MAXIMUM_REGULAR_HOURS : timeCard.getWorkHours();
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
