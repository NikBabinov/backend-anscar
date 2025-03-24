package ru.anscar.nikbabinov.constants;

import lombok.Getter;

@Getter
public enum RoleValue {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String value;

    RoleValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
