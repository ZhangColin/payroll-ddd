package payroll.domain;

import lombok.Getter;

import java.time.LocalDate;

/**
 * @author colin
 */
@Getter
public class TimeCard implements Comparable<TimeCard> {
    public static final int MAXIMUM_REGULAR_HOURS = 8;
    private LocalDate workDay;
    private int workHours;

    public TimeCard(LocalDate workDay, int workHours) {
        this.workDay = workDay;
        this.workHours = workHours;
    }

    @Override
    public int compareTo(TimeCard o) {
        if (workDay.isBefore(o.workDay)) {
            return -1;
        } else if (workDay.isAfter(o.workDay)) {
            return 1;
        } else {
            return 0;
        }
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
