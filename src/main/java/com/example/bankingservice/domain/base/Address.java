package com.example.bankingservice.domain.base;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String street;
    private String city;
    private String state;
    private String postal;
    private String country;


    public String toSql() {
        String address = "";
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                address += value.toString() + ",";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return address;
    }

    public static Address toAddress(String string) {
        Address address = new Address();
        String[] stringAddress = string.split(",");
        address.setStreet(stringAddress[0]);
        address.setCity(stringAddress[1]);
        address.setState(stringAddress[2]);
        address.setPostal(stringAddress[3]);
        address.setCountry(stringAddress[4]);
        return address;
    }
}
