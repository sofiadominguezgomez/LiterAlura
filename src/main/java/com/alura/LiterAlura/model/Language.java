package com.alura.LiterAlura.model;

public enum Language {
    EN("en"),
    ES("es");

    private String gutendexLanguage;

    Language(String gutendexLanguage) {
        this.gutendexLanguage = gutendexLanguage;
    }

    public String getGutendexLanguage() {
        return gutendexLanguage;
    }

    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.gutendexLanguage.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unknown language code: " + text);
    }
}
