package net.nm_weapons_pack.utils.text_formatting;

import net.minecraft.text.TranslatableText;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.utils.NmStyle;

import java.util.ArrayList;
import java.util.List;

public class TextFormatter {
    private List<TranslatableText> abilityString = new ArrayList<>();
    private String abilityText;

    public TextFormatter (String text) {
        abilityText = text;
    }

    public TranslatableText getText() {
        validate();
        TranslatableText text = abilityString.remove(0);
        for (TranslatableText textElement : abilityString) {
            text.append(textElement);
        }
        return text;
    }

    private void validate() {
        TextTag currentStartingTag = null;
        String currentString = "";
        boolean skippingTag = false;

        for (int i = 0; i < abilityText.length(); i++) {
            if (skippingTag) {
                if (abilityText.charAt(i) == '>') {
                    skippingTag = false;
                }
            } else {
                // Normal text
                if (abilityText.charAt(i) != '<') {
                    // Add text to current text in tag
                    currentString = currentString + abilityText.charAt(i);

                // Tag symbol start
                } else {
                    // Ending tag
                    if (currentStartingTag != null) {
                        TextTag currentEndingTag = getTag(abilityText.substring(i));
                        if (currentEndingTag == currentStartingTag) {
                            abilityString.add(makeTextWithTag(currentString, currentEndingTag));
                            currentStartingTag = null;
                            currentString = "";
                        } else {
                            NmWeaponsPack.warnMsg("Can't mix tags inside!");
                            throw new RuntimeException();
                        }
                        skippingTag = true;

                    // Starting tag
                    } else {
                        abilityString.add(makeTextWithTag(currentString, currentStartingTag));
                        currentStartingTag = getTag(abilityText.substring(i));
                        currentString = "";
                        skippingTag = true;
                    }
                }
            }
        }
    }

    private TextTag getTag(String string) {
        String currentTagChars = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) != '>') {
                currentTagChars = currentTagChars + string.charAt(i);
            } else {
                break;
            }
        }
        currentTagChars = currentTagChars.replace("<", "");
        TextTag tag = TextTag.getTag(currentTagChars);
        if (tag == null) {
            NmWeaponsPack.warnMsg("Invalid weapon tag " + currentTagChars);
            throw new RuntimeException();
        } else {
            return tag;
        }
    }

    private TranslatableText makeTextWithTag(String text, TextTag tag) {
        if (tag == null) {
            return (TranslatableText) new TranslatableText(text).setStyle(NmStyle.DESCRIPTION);
        } else {
            return (TranslatableText) switch (tag) {
                case HP -> new TranslatableText(text).setStyle(NmStyle.HEALTH);
                case DEF -> new TranslatableText(text).setStyle(NmStyle.DEFENCE);
                case DMG -> new TranslatableText(text).setStyle(NmStyle.DAMAGE);
                case EFF -> new TranslatableText(text).setStyle(NmStyle.EFFECT);
                case SPE -> new TranslatableText(text).setStyle(NmStyle.SPEED);
                case SPEC -> new TranslatableText(text).setStyle(NmStyle.SPECIAL);
            };
        }
    }
}
