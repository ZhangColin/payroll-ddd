package payroll.core.gateway.persistence.exception;

/**
 * @author colin
 */
public class InitializeEntityManagerException extends RuntimeException {
    public InitializeEntityManagerException() {
        super("Failed to initialize Entity Manager.");
    }

    public InitializeEntityManagerException(String message) {
        super(message);
    }
}
