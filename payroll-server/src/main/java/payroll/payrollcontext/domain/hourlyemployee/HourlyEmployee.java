package payroll.payrollcontext.domain.hourlyemployee;

import lombok.Getter;
import org.hibernate.annotations.DiscriminatorOptions;
import payroll.core.domain.AbstractEntity;
import payroll.core.domain.AggregateRoot;
import payroll.employeeontext.domain.EmployeeId;
import payroll.payrollcontext.domain.Salary;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author colin
 */
@Entity
@Table(name = "employees")
@DiscriminatorColumn(name = "employeeType", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorOptions(force = true)
@DiscriminatorValue(value = "0")
@Getter
public class HourlyEmployee extends AbstractEntity<EmployeeId> implements AggregateRoot<HourlyEmployee> {
    public static final double OVERTIME_FACTOR = 1.5;

    @EmbeddedId
    private EmployeeId employeeId;

    @Embedded
    private Salary salaryOfHour;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employeeId", nullable = false)
    private List<TimeCard> timeCards = new ArrayList<>();

    protected HourlyEmployee() {

    }

    public HourlyEmployee(EmployeeId employeeId, Salary salaryOfHour) {
        this(employeeId, salaryOfHour, new ArrayList<>());
    }

    public HourlyEmployee(EmployeeId employeeId, Salary salaryOfHour, List<TimeCard> timeCards) {
        this.employeeId = employeeId;
        this.salaryOfHour = salaryOfHour;
        if (!Objects.isNull(timeCards)) {
            this.timeCards = timeCards;
        }
    }

    public Payroll payroll(Period settlementPeriod) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(employeeId, settlementPeriod.getBeginDate(), settlementPeriod.getEndDate(), Salary.zero());
        }
        final Salary regularSalary = calculateRegularSalary(settlementPeriod);
        final Salary overtimeSalary = calculateOvertimeSalary(settlementPeriod);
        final Salary totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(
                employeeId, settlementPeriod.getBeginDate(),
                settlementPeriod.getEndDate(),
                totalSalary);
    }

    private Salary calculateOvertimeSalary(Period period) {
        final int overtimeHours = filterByPeriod(period)
                .filter(TimeCard::isOvertime)
                .map(TimeCard::getOvertimeWorkHours)
                .reduce(0, Integer::sum);
        return salaryOfHour.multiply(OVERTIME_FACTOR).multiply(overtimeHours);
    }

    private Salary calculateRegularSalary(Period period) {
        final int regularHours = filterByPeriod(period)
                .map(TimeCard::getRegularWorkHours)
                .reduce(0, Integer::sum);
        return salaryOfHour.multiply(regularHours);
    }

    private Stream<TimeCard> filterByPeriod(Period period) {
        return timeCards.stream()
                .filter(timeCard -> timeCard.isIn(period));
    }

    public void submit(List<TimeCard> submittedTimeCards) {
        for (TimeCard timeCard : submittedTimeCards) {
            this.submit(timeCard);
        }
    }

    public void remove(TimeCard removedTimeCard) {
        this.timeCards.remove(removedTimeCard);
    }

    public void submit(TimeCard submittedTimeCard) {
        if (!this.timeCards.contains(submittedTimeCard)) {
            this.timeCards.add(submittedTimeCard);
        }
    }

    @Override
    public EmployeeId id() {
        return this.employeeId;
    }

    @Override
    public HourlyEmployee root() {
        return this;
    }
}
