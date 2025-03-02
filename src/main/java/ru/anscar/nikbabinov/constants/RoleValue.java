package ru.anscar.nikbabinov.constants;

public enum RoleValue {

    READ("read"),
    WRITE("write"),
    ADMIN("admin");

    private final String value;

    private RoleValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
