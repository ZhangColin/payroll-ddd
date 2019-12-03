package payroll.core.gateway.persistence;

import payroll.core.domain.AggregateRoot;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<E extends AggregateRoot> {
    Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<E> root);
}
