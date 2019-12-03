package payroll.payrollcontext.domain.hourlyemployee;

import lombok.Getter;
import payroll.core.domain.AbstractEntity;
import payroll.core.domain.AggregateRoot;
import payroll.employeeontext.domain.EmployeeId;
import payroll.payrollcontext.domain.Money;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author colin
 */
@Entity
@Table(name = "employees")
@Getter
public class HourlyEmployee extends AbstractEntity<EmployeeId> implements AggregateRoot<HourlyEmployee> {
    public static final double OVERTIME_FACTOR = 1.5;

    @EmbeddedId
    private EmployeeId employeeId;

    @Embedded
    private Money salaryOfHour;

    @OneToMany
    @JoinColumn(name = "employeeId", nullable = false)
    private List<TimeCard> timeCards = new ArrayList<>();

    private HourlyEmployee() {

    }

    public HourlyEmployee(EmployeeId employeeId, Money salaryOfHour) {
        this(employeeId, salaryOfHour, new ArrayList<>());
    }

    public HourlyEmployee(EmployeeId employeeId, Money salaryOfHour, List<TimeCard> timeCards) {
        this.employeeId = employeeId;
        this.timeCards = timeCards;
        this.salaryOfHour = salaryOfHour;
    }

    public Payroll payroll(Period period) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(employeeId, period.getBeginDate(), period.getEndDate(), Money.zero());
        }
        final Money regularSalary = calculateRegularSalary();
        final Money overtimeSalary = calculateOvertimeSalary();
        final Money totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(
                employeeId, period.getBeginDate(),
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

    @Override
    public EmployeeId id() {
        return this.employeeId;
    }

    @Override
    public HourlyEmployee root() {
        return this;
    }
}
