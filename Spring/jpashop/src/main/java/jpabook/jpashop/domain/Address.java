package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
}
