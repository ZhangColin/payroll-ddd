package payroll.domain;

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
        return null;
    }
}
