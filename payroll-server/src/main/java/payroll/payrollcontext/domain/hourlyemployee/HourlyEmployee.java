package payroll.payrollcontext.domain.hourlyemployee;

import lombok.Getter;
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
    private Salary salaryOfHour;

    @OneToMany
    @JoinColumn(name = "employeeId", nullable = false)
    private List<TimeCard> timeCards = new ArrayList<>();

    private HourlyEmployee() {

    }

    public HourlyEmployee(EmployeeId employeeId, Salary salaryOfHour) {
        this(employeeId, salaryOfHour, new ArrayList<>());
    }

    public HourlyEmployee(EmployeeId employeeId, Salary salaryOfHour, List<TimeCard> timeCards) {
        this.employeeId = employeeId;
        this.timeCards = timeCards;
        this.salaryOfHour = salaryOfHour;
    }

    public Payroll payroll(Period period) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(employeeId, period.getBeginDate(), period.getEndDate(), Salary.zero());
        }
        final Salary regularSalary = calculateRegularSalary();
        final Salary overtimeSalary = calculateOvertimeSalary();
        final Salary totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(
                employeeId, period.getBeginDate(),
                period.getEndDate(),
                totalSalary);
    }

    private Salary calculateOvertimeSalary() {
        final int overtimeHours = timeCards.stream()
                .filter(TimeCard::isOvertime)
                .map(TimeCard::getOvertimeWorkHours)
                .reduce(0, Integer::sum);
        return salaryOfHour.multiply(OVERTIME_FACTOR).multiply(overtimeHours);
    }

    private Salary calculateRegularSalary() {
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
