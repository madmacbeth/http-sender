package com.macbeth.enumeration;

public enum MediaTypeEnum {
    APPLICATION_JSON("application/json");
    private String value;

    MediaTypeEnum(String value) {
        this.value = value;
    }
}
