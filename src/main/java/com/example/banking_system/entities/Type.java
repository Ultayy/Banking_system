package com.example.banking_system.entities;

public enum Type{
    PRODUCT(1),
    SERVICE(2);

    private final int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
