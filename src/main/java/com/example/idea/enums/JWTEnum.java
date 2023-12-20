package com.example.idea.enums;

public enum JWTEnum {
    AUTHORITIES_KEY("auth");

    private String value;
    JWTEnum(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }
}
