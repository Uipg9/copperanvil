# Copper Anvil

A copper anvil that works exactly like the vanilla anvil (repair, combine, rename, same XP costs and
"Too Expensive" limit, falls and hurts like an anvil) but wears out **twice as fast**: each use has a
24% chance (vanilla: 12%) to damage it. Copper Anvil -> Chipped -> Damaged -> breaks.

Fabric mod for Minecraft 26.3 (Java 25, Mojang names). Requires Fabric API only.

## Recipe

Vanilla anvil shape with copper: top row 3 blocks of copper, middle 1 copper ingot, bottom row
3 copper ingots. Found in the Functional Blocks creative tab after the vanilla anvils.

## Development

Build and test: `.\gradlew.bat build`. Full check (build, unit tests, in-game client gametest):
`.\scripts\verify.ps1`. The wear rule lives in `WearPolicy` (pure Java, unit-tested); the mixins
`AnvilMenuMixin` (damage chance) and `AnvilBlockMixin` (next stage) apply it only to copper anvils.

License: CC0-1.0.
