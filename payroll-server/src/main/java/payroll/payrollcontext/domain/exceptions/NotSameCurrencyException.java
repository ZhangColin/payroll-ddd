package payroll.payrollcontext.domain.exceptions;

public class NotSameCurrencyException extends RuntimeException {
    public NotSameCurrencyException(String message) {
        super(message);
    }
}
