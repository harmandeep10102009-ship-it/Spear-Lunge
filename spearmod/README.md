# Spear Mod (Minecraft 1.16.5, Fabric)

Adds one item, the Netherite Spear, and one enchantment, Lunge III (spear only).

## Lunge III
- Triggers on a jab that hits an entity. Missed jabs do nothing in this version.
- Forward impulse 1.37 blocks per tick (27.48 blocks per second), horizontal to your look direction.
- Costs 1 durability and 12 exhaustion. Does not trigger while riding or gliding, or with food below 7 (outside creative).

## Netherite Spear
- Jab damage 5.
- Charge damage on hit: 1.2 x closing speed in blocks per second, minimum 4.6 blocks per second.
- Crafted with 2 sticks and 1 netherite ingot, placed diagonally from bottom-left to top-right.

## Not implemented
- Charge stages, dismounting riders, reach changes, Tired/Disengaged states, the Mob Kabob advancement.
- Custom texture. The spear uses the vanilla netherite sword texture.

## Unverified
- The charge formula is an approximation. Sources disagree on the exact formula.
- Dependency versions in build.gradle are from memory. Check them against the current Fabric 1.16.5 template.
- The project has not been compiled or run.

## Build and test
Requires JDK 8. Run `gradle wrapper`, then `./gradlew build`. Test in-game with `./gradlew runClient`.
To test Lunge, hold a netherite spear and run `/enchant @s spearmod:lunge 3`.
