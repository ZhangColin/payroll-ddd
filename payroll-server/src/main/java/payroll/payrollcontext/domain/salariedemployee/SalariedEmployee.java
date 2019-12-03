package payroll.payrollcontext.domain.salariedemployee;

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

@Entity
@Table(name = "employees")
@DiscriminatorColumn(name = "employeeType", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorOptions(force = true)
@DiscriminatorValue(value = "1")
@Getter
public class SalariedEmployee extends AbstractEntity<EmployeeId> implements AggregateRoot<SalariedEmployee> {
    private static final int WORK_DAYS_OF_MONTH = 22;

    @EmbeddedId
    private EmployeeId employeeId;

    @Embedded
    private Salary salaryOfMonth;

    @ElementCollection
    @CollectionTable(name = "absences", joinColumns = @JoinColumn(name = "employeeId"))
    private List<Absence> absences = new ArrayList<>();

    private SalariedEmployee() {

    }

    public SalariedEmployee(EmployeeId employeeId, Salary salaryOfMonth) {
        this(employeeId, salaryOfMonth, new ArrayList<>());
    }
    public SalariedEmployee(EmployeeId employeeId, Salary salaryOfMonth, List<Absence> absences) {
        this.employeeId = employeeId;
        this.salaryOfMonth = salaryOfMonth;
        this.absences = absences;
    }

    public Payroll payroll(Period settlementPeriod) {
        final Salary salaryOfDay = salaryOfMonth.divide(WORK_DAYS_OF_MONTH);

        final Salary deduction = absences.stream()
                .filter(absence -> !absence.isPaidLeave())
                .map(absence -> salaryOfDay.multiply(absence.deductionRation()))
                .reduce(Salary.zero(), (m, total) -> total.add(m));
        return new Payroll(employeeId, settlementPeriod.getBeginDate(), settlementPeriod.getEndDate(), salaryOfMonth.subtract(deduction));
    }

    @Override
    public EmployeeId id() {
        return employeeId;
    }

    @Override
    public SalariedEmployee root() {
        return this;
    }
}
