package dev.brian.copperanvil.client;

import dev.brian.copperanvil.Copperanvil;
import dev.brian.copperanvil.ModBlocks;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * In-game check: place a copper anvil, open it with a real right-click, rename an item through
 * the anvil menu, and confirm the copper wear chain. Screenshots land in
 * {@code build\run\clientGameTest\screenshots}.
 */
public class CopperanvilClientGameTest implements FabricClientGameTest {
	private static final BlockPos ANVIL_POS = new BlockPos(3, 101, 6);

	private static void expect(boolean ok, String message) {
		if (!ok) {
			throw new AssertionError(message);
		}
	}

	@Override
	public void runTest(ClientGameTestContext context) {
		expect(FabricLoader.getInstance().isModLoaded(Copperanvil.MOD_ID), Copperanvil.MOD_ID + " is not loaded");

		context.waitForScreen(TitleScreen.class);
		try (TestSingleplayerContext world = context.worldBuilder().create()) {
			context.waitTicks(100);

			// Wear chain: copper -> chipped -> damaged -> breaks, facing preserved; vanilla untouched.
			String chain = world.getServer().computeOnServer(server -> {
				BlockState copper = ModBlocks.COPPER_ANVIL.defaultBlockState().setValue(AnvilBlock.FACING, Direction.EAST);
				BlockState chipped = AnvilBlock.damage(copper);
				if (chipped == null || !chipped.is(ModBlocks.CHIPPED_COPPER_ANVIL)) return "copper -> " + chipped;
				if (chipped.getValue(AnvilBlock.FACING) != Direction.EAST) return "facing lost";
				BlockState damaged = AnvilBlock.damage(chipped);
				if (damaged == null || !damaged.is(ModBlocks.DAMAGED_COPPER_ANVIL)) return "chipped -> " + damaged;
				if (AnvilBlock.damage(damaged) != null) return "damaged copper anvil should break";
				if (!AnvilBlock.damage(Blocks.ANVIL.defaultBlockState()).is(Blocks.CHIPPED_ANVIL)) return "vanilla changed";
				return null;
			});
			expect(chain == null, "wear chain: " + chain);

			world.getServer().runCommand("gamemode survival @p");
			world.getServer().runCommand("fill 0 100 0 8 100 8 stone");
			world.getServer().runCommand("fill 0 101 0 8 104 8 air");
			world.getServer().runCommand("setblock 3 101 6 copperanvil:copper_anvil[facing=east]");
			world.getServer().runCommand("tp @p 3.5 101 4.5 0 20");
			world.getServer().runCommand("xp add @p 30 levels");
			world.getServer().runCommand("item replace entity @p weapon.mainhand with minecraft:air");
			context.waitTicks(20);
			context.takeScreenshot("copper_anvil_world");

			// Open the menu with a real right-click (stillValid needs the block in #minecraft:anvil).
			context.getInput().lookAt(ANVIL_POS);
			context.waitTicks(2);
			context.getInput().pressKey(options -> options.keyUse);
			context.waitForScreen(AnvilScreen.class);

			// Rename a diamond sword through the anvil menu.
			String rename = world.getServer().computeOnServer(server -> {
				ServerPlayer player = server.getPlayerList().getPlayers().getFirst();
				if (!(player.containerMenu instanceof AnvilMenu menu)) return "server menu is " + player.containerMenu;
				if (!menu.stillValid(player)) return "menu not valid for copper anvil";
				menu.getSlot(AnvilMenu.INPUT_SLOT).set(new ItemStack(Items.DIAMOND_SWORD));
				menu.setItemName("Copper Blade");
				menu.createResult();
				ItemStack result = menu.getSlot(AnvilMenu.RESULT_SLOT).getItem();
				if (!result.is(Items.DIAMOND_SWORD)) return "result was " + result;
				if (!"Copper Blade".equals(result.getHoverName().getString())) return "name was " + result.getHoverName().getString();
				if (menu.getCost() != 1) return "rename cost was " + menu.getCost();
				return null;
			});
			expect(rename == null, "rename: " + rename);
			context.waitTicks(5);
			context.takeScreenshot("copper_anvil_menu");
		}
	}
}
