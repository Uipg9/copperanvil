package dev.brian.copperanvil.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.brian.copperanvil.CopperAnvilBlock;
import dev.brian.copperanvil.WearPolicy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/** Doubles the per-use damage chance (vanilla 0.12) when the anvil being used is a copper one. */
@Mixin(AnvilMenu.class)
abstract class AnvilMenuMixin {
	@ModifyConstant(method = "lambda$onTake$0", constant = @Constant(floatValue = 0.12f))
	private static float copperanvil$damageChance(float vanilla, @Local(argsOnly = true) Level level,
		@Local(argsOnly = true) BlockPos pos) {
		return level.getBlockState(pos).getBlock() instanceof CopperAnvilBlock ? WearPolicy.DAMAGE_CHANCE : vanilla;
	}
}
