package com.example.banking_system.entities;

public enum Status {
    CANCEL(0),
    APPROVE(1);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
