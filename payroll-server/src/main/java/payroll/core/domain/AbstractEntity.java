package payroll.core.domain;

/**
 * @author colin
 */
public abstract class AbstractEntity<ID extends Identity> {
    public abstract ID id();
}
