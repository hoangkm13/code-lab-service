package com.example.codelabsvc.constant;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    
    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<String> getValues(){
        List<String> values = new ArrayList<>();
        for (Role role : Role.values()) {
            values.add(role.getValue());
        }
        return values;
    }
}
