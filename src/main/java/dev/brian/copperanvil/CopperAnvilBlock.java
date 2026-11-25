package dev.brian.copperanvil;

import net.minecraft.world.level.block.AnvilBlock;

/** A vanilla anvil that remembers its wear stage; the faster wear is applied by the mixins. */
public class CopperAnvilBlock extends AnvilBlock {
	private final WearPolicy.Stage stage;

	public CopperAnvilBlock(WearPolicy.Stage stage, Properties properties) {
		super(properties);
		this.stage = stage;
	}

	public WearPolicy.Stage stage() {
		return stage;
	}
}
