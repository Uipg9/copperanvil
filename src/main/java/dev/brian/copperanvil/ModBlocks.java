package dev.brian.copperanvil;

import java.util.EnumMap;
import java.util.Map;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {
	private static final Map<WearPolicy.Stage, CopperAnvilBlock> BY_STAGE = new EnumMap<>(WearPolicy.Stage.class);

	public static final CopperAnvilBlock COPPER_ANVIL = register(WearPolicy.Stage.INTACT, Blocks.ANVIL);
	public static final CopperAnvilBlock CHIPPED_COPPER_ANVIL = register(WearPolicy.Stage.CHIPPED, Blocks.CHIPPED_ANVIL);
	public static final CopperAnvilBlock DAMAGED_COPPER_ANVIL = register(WearPolicy.Stage.DAMAGED, Blocks.DAMAGED_ANVIL);

	private ModBlocks() {
	}

	private static CopperAnvilBlock register(WearPolicy.Stage stage, Block vanilla) {
		Identifier id = Copperanvil.id(stage.path());
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
		CopperAnvilBlock block = Registry.register(BuiltInRegistries.BLOCK, blockKey,
			new CopperAnvilBlock(stage, BlockBehaviour.Properties.ofFullCopy(vanilla).setId(blockKey)));
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
		Registry.register(BuiltInRegistries.ITEM, itemKey,
			new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix()));
		BY_STAGE.put(stage, block);
		return block;
	}

	public static CopperAnvilBlock forStage(WearPolicy.Stage stage) {
		return BY_STAGE.get(stage);
	}

	public static void initialize() {
		ResourceKey<CreativeModeTab> functional = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
			Identifier.withDefaultNamespace("functional_blocks"));
		CreativeModeTabEvents.modifyOutputEvent(functional).register(output -> output.insertAfter(
			Items.DAMAGED_ANVIL, COPPER_ANVIL, CHIPPED_COPPER_ANVIL, DAMAGED_COPPER_ANVIL));
	}
}
