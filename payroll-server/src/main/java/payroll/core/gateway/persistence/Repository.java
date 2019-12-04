package payroll.core.gateway.persistence;

import payroll.core.domain.AggregateRoot;
import payroll.core.domain.Identity;
import payroll.core.gateway.persistence.exception.InitializeEntityManagerException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class Repository<E extends AggregateRoot, ID extends Identity> {
    private final Class<E> entityClass;
    private final EntityManager entityManager;
    private final TransactionScope transactionScope;

    public Repository(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.transactionScope = new TransactionScope(entityManager);
    }

    public Optional<E> findById(ID id) {
        requireEntityManagerNotNull();

        if (entityManager == null) {
            throw new InitializeEntityManagerException();
        }

        final E root = entityManager.find(entityClass, id);
        if (root == null) {
            return Optional.empty();
        }
        return Optional.of(root);
    }

    public List<E> findAll() {
        requireEntityManagerNotNull();

        final CriteriaQuery<E> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
        query.select(query.from(entityClass));
        return entityManager.createQuery(query).getResultList();
    }

    public List<E> findBy(Specification<E> specification) {
        requireEntityManagerNotNull();

        if (specification == null) {
            return findAll();
        }

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        final Root<E> root = query.from(entityClass);

        final Predicate predicate = specification.toPredicate(criteriaBuilder, query, root);
        query.where(predicate);

        final TypedQuery<E> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public void saveOrUpdate(E entity) {
        requireEntityManagerNotNull();

        if (entity == null) {
            // todo: logging it
            return;
        }

        transactionScope.using(em->{
            if (em.contains(entity)) {
                em.merge(entity);
            } else {
                em.persist(entity);
            }
        });
    }

    public void delete(E entity) {
        requireEntityManagerNotNull();

        if (entity == null) {
            // todo: logging it
            return;
        }

        if (!entityManager.contains(entity)) {
            return;
        }

        transactionScope.using(em->em.remove(entity));
    }

    private void requireEntityManagerNotNull() {
        if (entityManager == null) {
            throw new InitializeEntityManagerException();
        }
    }

    protected void finalize() {
        entityManager.close();
    }
}
