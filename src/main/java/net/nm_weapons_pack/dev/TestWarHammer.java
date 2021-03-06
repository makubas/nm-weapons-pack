package net.nm_weapons_pack.dev;

import net.nm_weapons_pack.abilities.AbilityTooltip;
import net.nm_weapons_pack.abilities.implemented.BleedingWeapon;
import net.nm_weapons_pack.abilities.implemented.ShockWeapon;
import net.nm_weapons_pack.items.weapons.types.melee.NmWarHammer;

import java.util.ArrayList;
import java.util.List;

public class TestWarHammer extends NmWarHammer implements BleedingWeapon {
    public TestWarHammer() {
        super("test_war_hammer");
    }

    @Override
    public List<AbilityTooltip> getImplementedAbilities() {
       return new ArrayList<>() {{
           add(BleedingWeapon.getTooltip());
       }};
    }

    @Override
    public float getBleedingProbability() {
        return 1F;
    }
}

