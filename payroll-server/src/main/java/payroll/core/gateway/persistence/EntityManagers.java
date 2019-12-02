package payroll.core.gateway.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * @author colin
 */
public class EntityManagers {
    static EntityManager from(String persistenceUnitName) {
        return Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
    }
}
