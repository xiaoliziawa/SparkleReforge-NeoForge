package miyucomics.sparkle.mixin;

import miyucomics.sparkle.Sparkle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Block.class)
public class BlockMixin {
	@Inject(method = "animateTick", at = @At("HEAD"))
	private void addParticles(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
		if (level.isClientSide() && random.nextFloat() < 0.2 && Sparkle.SPARKLY_BLOCKS.contains(state.getBlock())) {
			Direction direction = Direction.getRandom(random);
			Direction.Axis axis = direction.getAxis();
			double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
			double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
			double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
			level.addParticle(Sparkle.SPARKLE_PARTICLE.get(), pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0, 0, 0);
		}
	}
}