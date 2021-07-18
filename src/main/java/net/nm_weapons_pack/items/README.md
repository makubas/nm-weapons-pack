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

