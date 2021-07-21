# Weapons

## Main classes 
Every weapon type class extends *NmWeapon*.
There are 4 main weapon subclasses:

* **NmMeleeWeapon** - for weapons without any required projectiles - swords, war hammers, daggers
* **NmRangedWeapon** - for weapons that require you to have a projectile to use it - bows
* **NmMagicWeapon** - for weapons that does not require projectiles but are mostly ranged - staffs, wands
* **NmThrowableWeapon** - for throwable weapons that will be required to pickup after being thrown - spear, shuriken

### Weapon types classes

Currently available weapon type classes:

* **NmSwordWeapon**
* **NmDaggerWeapon**
* **NmBattleAxeWeapon**

* **NmBowWeapon**
* **NmLongBowWeapon**
  
* **NmStaffWeapon**
* **NmWandWeapon**
  
* **NmShurikenWeapon**
* **NmSpearWeapon**

## Projectiles

Each projectile has its own class which extends one of the minecraft's build in
classes that other projectiles uses! Projectiles can't do anything by their own,
to use it you need to have a corresponding **Ranged Weapon** in hand.

## Throwable weapons

Throwable weapons is the only type of weapon that will be lost after throwing.
To get it back, you need to come and pick it up.

## Spells

***Work in progress...***

Spells can only be used together with **Magic Weapons**. When you will have magic
weapon in hand, you will be able to select spell that you want to cast / use.

## Adding weapon

1. Config

Add `your_weapon_id.json` in `your_weapon_category` folder and copy there settings of
any other weapon from this category. After that, add your weapon config registry in
`weapon_registry.json` in main config directory.

2. Class

Create new class under weapons folder and name it YourWeapon.java.
Extend One of the abstract classes from items.weapons.types.

3. Writing code

Make your weapon, declare all needed method and your abilities.

4. Register it

Go to items.NmItems and register it like the ones that are already added.

## Abilities

### Adding ability to existing weapon

1. Implement the `LeftClickAbility`, `RightClickAbility`, `PassiveAbility` interface/s.

2. @Override needed method, for example:
```java
@Override
public AbilityTooltip getRightClickAbilityTooltip() {
    return new AbilityTooltip(
            "Test Right Click Ability", // Name
            "Heals you <hp>6HP<hp> and gives you <spe>+100% speed<spe>", // Description
            AbilityType.RIGHT_CLICK, // Type
            AbilityRarity.EPIC, // Rarity
            70 // Cooldown (0 for AbilityType.PASSIVE)
    );
}
```

3. Add `addAbilityTooltip(getRightClickAbilityTooltip());` in constructor
   (if you are adding > 1 ability, add `addEmptyTooltipLine();` between adding tooltip
   abilities)
   
4. Code your ability!

### Ability description formatting

Formatting tags available for creating ability description 
are listed in enum `utils.text_formatting.TextTag`.


## Weapon structure:
* Class under `/weapons`
* Config `.json` file under `/weapon_type` & `weapon_registry.json` addition
* Resources in `src/main/resources/assets/nm_weapons_pack`



