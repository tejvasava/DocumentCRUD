package com.document.main.enums;

public enum ResponseStatus {

	SUCCESS("SUCCESS"),
    FAIL("FAIL");

    private final String name;

    private ResponseStatus(String value) {
        this.name = value;
    }

    public String value() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
