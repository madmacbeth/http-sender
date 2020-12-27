package com.macbeth.impl.platform.god.enumeration;

import lombok.Getter;

@Getter
public enum ResponseContentTypeEnum {
    JSON("json"), XML("xml");
    private String value;

    ResponseContentTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
