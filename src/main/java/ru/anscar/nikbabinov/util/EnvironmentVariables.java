package ru.anscar.nikbabinov.util;

public class EnvironmentVariables {

    public static String getDbUrl() {
        String dbUrl;
        return dbUrl = System.getenv("DB_URL");
    }

    public static String getDbUsername() {
        String dbUsername;
        return dbUsername = System.getenv("DB_USERNAME");
    }

    public static String getDbPassword() {
        String dbPassword;
        return dbPassword = System.getenv("DB_PASSWORD");
    }
}
