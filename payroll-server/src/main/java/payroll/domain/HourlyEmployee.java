package payroll.domain;

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

        Collections.sort(timeCards);

        return new Payroll(timeCards.get(0).getWorkDay(), timeCards.get(timeCards.size()-1).getWorkDay(), salaryOfHour.multiple(totalHours));
    }
}
