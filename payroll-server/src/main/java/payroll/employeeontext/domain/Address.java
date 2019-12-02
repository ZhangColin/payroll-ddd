package payroll.employeeontext.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@EqualsAndHashCode
public class Address {
    private String country;
    private String province;
    private String city;
    private String street;
    private String zip;

    private Address() {
    }

    public Address(String country, String province, String city, String street, String zip) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.zip = zip;
    }
}
