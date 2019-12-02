package payroll.core.domain;

/**
 * @author colin
 */
public interface AggregateRoot<R extends AbstractEntity> {
    R root();
}
