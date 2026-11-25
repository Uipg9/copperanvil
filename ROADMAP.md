# Copper Anvil roadmap

## Done
- Blocks + block items `copper_anvil`, `chipped_copper_anvil`, `damaged_copper_anvil` (`CopperAnvilBlock`
  extends vanilla `AnvilBlock`, properties copied from the matching vanilla anvil).
- Wear: 24% chance per use (`AnvilMenuMixin` swaps the 0.12 constant only for copper anvils);
  copper -> chipped -> damaged -> breaks (`AnvilBlockMixin` on `AnvilBlock.damage`, facing kept).
  Rule in `WearPolicy`, unit-tested.
- In `#minecraft:anvil` block + item tags (menu `stillValid`, falling-anvil damage), `#mineable/pickaxe`,
  loot tables drop each stage as itself.
- Recipe (vanilla anvil shape, copper blocks/ingots), Functional Blocks tab after Damaged Anvil, lang,
  models on `template_anvil`, textures.
- Client gametest: wear chain, real right-click opens AnvilScreen, server menu stillValid, rename typed
  into the name box with real key input gives "Copper Blade" for 1 level; screenshots show the texture.

## Not done / untested
- The 24% rate itself is only unit-tested (`WearPolicy`); the mixin is verified to apply (defaultRequire=1)
  but no statistical in-game check.
- Falling copper anvil (fall damage, landing sound, damage on landing) relies on vanilla code paths
  and the anvil tag; not exercised in the gametest.
- Taking the result (XP charge + the 24% wear roll in-game), repair/combine and "Too Expensive" are
  vanilla code paths and not exercised in the gametest.
- Not published; no GitHub repo.
