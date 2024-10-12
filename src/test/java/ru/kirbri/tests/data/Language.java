package ru.kirbri.tests.data;

public enum Language {
    RU("Что такое Selenide?"), EN("What is Selenide?");
    private final String decription;

    Language(String decription) {
        this.decription = decription;
    }

    public String getDecription() {
        return decription;
    }
}
