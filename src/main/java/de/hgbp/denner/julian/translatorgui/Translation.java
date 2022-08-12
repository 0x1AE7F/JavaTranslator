package de.hgbp.denner.julian.translatorgui;

public class Translation {
    private String original;
    private String translation;

    public Translation(String original, String translation) {
        this.original = original;
        this.translation = translation;
    }

    public String getOriginal() {
        return original;
    }

    public String getTranslation() {
        return translation;
    }
}