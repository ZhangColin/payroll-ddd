package payroll.domain;

import java.time.LocalDate;

/**
 * @author colin
 */
public class TimeCard {
    private LocalDate workDay;
    private int workHours;

    public TimeCard(LocalDate workDay, int workHours) {
        this.workDay = workDay;
        this.workHours = workHours;
    }
}
