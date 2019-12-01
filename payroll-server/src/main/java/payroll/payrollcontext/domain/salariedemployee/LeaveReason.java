package payroll.payrollcontext.domain.salariedemployee;

import lombok.Getter;

@Getter
public enum LeaveReason {
    /**
     * 病假
     */
    SICK_LEAVE(0.5),
    /**
     * 事假
     */
    CASUAL_LEAVE(0.5),
    /**
     * 产假
     */
    MATERNITY_LEAVE(0),
    /**
     * 丧假
     */
    BEREAVEMENT_LEAVE(0),
    /**
     * 拒绝请假
     */
    DISAPPROVED_LEAVE(0);

    private double deductionRation;

    LeaveReason(double deductionRation) {
        this.deductionRation = deductionRation;
    }

    public boolean isPaidLeave() {
        return deductionRation == 0;
    }
}
