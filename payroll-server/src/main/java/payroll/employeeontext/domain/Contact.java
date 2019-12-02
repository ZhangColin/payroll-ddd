package payroll.employeeontext.domain;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Contact {
    private String mobilePhone;
    private String homePhone;
    private String officePhone;

    private Contact() {
    }

    private Contact(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public static Contact of(String mobilePhone) {
        return new Contact(mobilePhone);
    }
}
