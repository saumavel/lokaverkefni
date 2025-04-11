package is.hi.hbv202g.lokaverkefni.options;

public enum Language {
    ENGLISH("English"),
    ICELANDIC("Icelandic"),;

    private final String languageName;

    Language(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName() {
        return languageName;
    }

}
