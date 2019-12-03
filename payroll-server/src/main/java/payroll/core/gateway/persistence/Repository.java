package payroll.core.gateway.persistence;

import payroll.core.domain.AggregateRoot;
import payroll.core.domain.Identity;
import payroll.core.gateway.persistence.exception.InitializeEntityManagerException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class Repository<E extends AggregateRoot, ID extends Identity> {
    private final Class<E> entityClass;
    private final EntityManager entityManager;

    public Repository(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    public Optional<E> findById(ID id) {
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
        final CriteriaQuery<E> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
        query.select(query.from(entityClass));
        return entityManager.createQuery(query).getResultList();
    }
}
