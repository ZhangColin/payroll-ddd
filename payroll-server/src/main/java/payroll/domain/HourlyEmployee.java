package payroll.domain;

import java.util.List;
import java.util.Objects;

/**
 * @author colin
 */
public class HourlyEmployee {
    public static final double OVERTIME_FACTOR = 1.5;
    private final List<TimeCard> timeCards;
    private final Money salaryOfHour;

    public HourlyEmployee(List<TimeCard> timeCards, Money salaryOfHour) {
        this.timeCards = timeCards;
        this.salaryOfHour = salaryOfHour;
    }

    public Payroll payroll(Period period) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(period.getBeginDate(), period.getEndDate(), Money.zero());
        }
        final Money regularSalary = calculateRegularSalary();
        final Money overtimeSalary = calculateOvertimeSalary();
        final Money totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(
                period.getBeginDate(),
                period.getEndDate(),
                totalSalary);
    }

    private Money calculateOvertimeSalary() {
        final int overtimeHours = timeCards.stream()
                .filter(TimeCard::isOvertime)
                .map(TimeCard::getOvertimeWorkHours)
                .reduce(0, Integer::sum);
        return salaryOfHour.multiply(OVERTIME_FACTOR).multiply(overtimeHours);
    }

    private Money calculateRegularSalary() {
        final int regularHours = timeCards.stream()
                .map(TimeCard::getRegularWorkHours)
                .reduce(0, Integer::sum);
        return salaryOfHour.multiply(regularHours);
    }

}
