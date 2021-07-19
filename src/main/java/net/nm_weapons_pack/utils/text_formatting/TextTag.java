package net.nm_weapons_pack.utils.text_formatting;

public enum TextTag {
    HP,
    DEF,
    DMG,
    SPE,
    EFF,
    SPEC;

    public static TextTag getTag(String tag) {
        return switch (tag) {
            case "hp" -> HP;
            case "def" -> DEF;
            case "dmg" -> DMG;
            case "spe" -> SPE;
            case "eff" -> EFF;
            case "spec" -> SPEC;
            default -> null;
        };
    }
}
