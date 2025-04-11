package is.hi.hbv202g.lokaverkefni.options;

public enum Language {
    ENGLISH("English"),
    ICELANDIC("Íslenska");

    private final String languageName;

    Language(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public static Language fromString(String text) {
        for (Language lang : Language.values()) {
            if (lang.languageName.equalsIgnoreCase(text)) {
                return lang;
            }
        }
        return null; // or throw an exception
    }
}
