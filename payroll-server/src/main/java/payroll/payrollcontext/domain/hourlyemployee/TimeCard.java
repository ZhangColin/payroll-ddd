package payroll.payrollcontext.domain.hourlyemployee;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author colin
 */
@Getter
@Entity
@Table(name = "timecards")
public class TimeCard {
    public static final int MAXIMUM_REGULAR_HOURS = 8;

    @Id
    @GeneratedValue
    private String id;
    private LocalDate workDay;
    private int workHours;

    private TimeCard() {

    }

    public TimeCard(LocalDate workDay, int workHours) {
        this.workDay = workDay;
        this.workHours = workHours;
    }

    public int getOvertimeWorkHours() {
        return getWorkHours() - MAXIMUM_REGULAR_HOURS;
    }

    public boolean isOvertime() {
        return getWorkHours() > MAXIMUM_REGULAR_HOURS;
    }

    public int getRegularWorkHours() {
        return isOvertime() ? MAXIMUM_REGULAR_HOURS : getWorkHours();
    }
}
