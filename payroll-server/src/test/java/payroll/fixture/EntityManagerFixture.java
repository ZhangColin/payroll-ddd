package payroll.fixture;

import payroll.core.gateway.persistence.EntityManagers;

import javax.persistence.EntityManager;

public class EntityManagerFixture {
    public static final String PERSISTENCE_UNIT_NAME = "PAYROLL_JPA";

    public static EntityManager createEntityManager() {
        return EntityManagers.from(PERSISTENCE_UNIT_NAME);
    }
}
