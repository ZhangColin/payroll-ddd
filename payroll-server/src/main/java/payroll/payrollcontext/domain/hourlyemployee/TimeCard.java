package payroll.payrollcontext.domain.hourlyemployee;

import lombok.Getter;
import payroll.payrollcontext.domain.Period;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author colin
 */
@Getter
@Entity
@Table(name = "timecards")
public class TimeCard {
    public static final int MAXIMUM_REGULAR_HOURS = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate workDay;
    private int workHours;

    protected TimeCard() {

    }

    public TimeCard(LocalDate workDay, int workHours) {
        this.workDay = workDay;
        this.workHours = workHours;
    }

    public boolean isIn(Period period) {
        return period.contains(workDay);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeCard timeCard = (TimeCard) o;
        return Objects.equals(workDay, timeCard.workDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workDay);
    }
}
