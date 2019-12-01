package payroll.payrollcontext.domain.salariedemployee;

import java.time.LocalDate;

public class Absence {
    private final String employeeId;
    private final LocalDate leaveDate;
    private final LeaveReason leaveReason;

    public Absence(String employeeId, LocalDate leaveDate, LeaveReason leaveReason) {
        this.employeeId = employeeId;
        this.leaveDate = leaveDate;
        this.leaveReason = leaveReason;
    }

    public boolean isPaidLeave() {
        return leaveReason.isPaidLeave();
    }

    public double deductionRation() {
        return leaveReason.getDeductionRation();
    }
}
