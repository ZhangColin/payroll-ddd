package payroll.core.domain;

import payroll.payrollcontext.domain.hourlyemployee.TimeCard;

import java.util.List;

/**
 * @author colin
 */
public abstract class AbstractEntity<ID extends Identity> {
    public abstract ID id();
}
