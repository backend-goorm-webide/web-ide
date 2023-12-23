package com.example.idea.enums;

public enum UserInterfaceMsg {
    VALIDATE_PHONE_NUMBER("전화번호 형식을 확인해주세요"),
    ;

    private String value;

    UserInterfaceMsg(String value) {
        this.value = value;
    }

    public String getName() {
        return name();
    }
    public String getValue() {
        return value;
    }
}
