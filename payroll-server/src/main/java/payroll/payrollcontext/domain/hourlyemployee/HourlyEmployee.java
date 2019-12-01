package payroll.payrollcontext.domain.hourlyemployee;

import payroll.payrollcontext.domain.Money;
import payroll.payrollcontext.domain.Payroll;
import payroll.payrollcontext.domain.Period;

import java.util.List;
import java.util.Objects;

/**
 * @author colin
 */
public class HourlyEmployee {
    public static final double OVERTIME_FACTOR = 1.5;
    private String employeeId;
    private final List<TimeCard> timeCards;
    private final Money salaryOfHour;

    public HourlyEmployee(String employeeId, List<TimeCard> timeCards, Money salaryOfHour) {
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

}
