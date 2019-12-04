package payroll.employeeontext.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author colin
 */
@Embeddable
public class Email {
    @Column(name = "email")
    private String value;

    public String value() {
        return this.value;
    }

    protected Email() {
    }

    public Email(String value) {
        this.value = value;
    }
}
