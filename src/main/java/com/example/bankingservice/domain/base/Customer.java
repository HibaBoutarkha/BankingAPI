package com.example.bankingservice.domain.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {

    private Long UUID;
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private String email;
    private Date birthdate;
    private Address birthAddress;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                map.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        map.put("role", "customer");
        map.replace("birthdate", birthdate, this.birthdateToString());
        return map;
    }


    private String birthdateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(this.birthdate);
        return formattedDate;
    }
}
