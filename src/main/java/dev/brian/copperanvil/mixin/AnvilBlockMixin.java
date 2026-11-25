package dev.brian.copperanvil.mixin;

import dev.brian.copperanvil.CopperAnvilBlock;
import dev.brian.copperanvil.ModBlocks;
import dev.brian.copperanvil.WearPolicy;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Copper anvils degrade into copper stages (used by the menu and by fall damage). */
@Mixin(AnvilBlock.class)
abstract class AnvilBlockMixin {
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private static void copperanvil$damage(BlockState state, CallbackInfoReturnable<BlockState> cir) {
		if (state.getBlock() instanceof CopperAnvilBlock copper) {
			WearPolicy.Stage next = copper.stage().next();
			cir.setReturnValue(next == null ? null
				: ModBlocks.forStage(next).defaultBlockState().setValue(AnvilBlock.FACING, state.getValue(AnvilBlock.FACING)));
		}
	}
}
