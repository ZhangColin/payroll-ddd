package payroll.payrollcontext.domain.salariedemployee;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
public class Absence {
    private LocalDate leaveDate;
    @Enumerated(EnumType.STRING)
    private LeaveReason leaveReason;

    private Absence() {

    }

    public Absence(LocalDate leaveDate, LeaveReason leaveReason) {
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
