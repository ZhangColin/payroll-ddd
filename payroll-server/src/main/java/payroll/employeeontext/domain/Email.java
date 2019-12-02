package payroll.employeeontext.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {
    @Column(name = "email")
    private String value;

    public String value() {
        return this.value;
    }
}
