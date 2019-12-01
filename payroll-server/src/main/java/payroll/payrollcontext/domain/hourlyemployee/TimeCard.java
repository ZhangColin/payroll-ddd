package payroll.payrollcontext.domain.hourlyemployee;

import lombok.Getter;

import java.time.LocalDate;

/**
 * @author colin
 */
@Getter
public class TimeCard {
    public static final int MAXIMUM_REGULAR_HOURS = 8;
    private LocalDate workDay;
    private int workHours;

    public TimeCard(LocalDate workDay, int workHours) {
        this.workDay = workDay;
        this.workHours = workHours;
    }

    int getOvertimeWorkHours() {
        return getWorkHours() - MAXIMUM_REGULAR_HOURS;
    }

    boolean isOvertime() {
        return getWorkHours() > MAXIMUM_REGULAR_HOURS;
    }

    int getRegularWorkHours() {
        return isOvertime() ? MAXIMUM_REGULAR_HOURS : getWorkHours();
    }
}
