package miyucomics.sparkle.mixin;

import miyucomics.sparkle.Sparkle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {
	protected Random random = new Random();
	@Shadow public abstract Level level();
	@Shadow public abstract double getX();
	@Shadow public abstract double getY();
	@Shadow public abstract double getZ();

	@Inject(method = "tick", at = @At("HEAD"))
	private void addParticles(CallbackInfo info) {
		if (!level().isClientSide())
			return;
		if (Sparkle.SPARKLY_ENTITIES.contains(((Entity) (Object) this).getType()) && random.nextFloat() < 0.1F) {
			double positionX = (getX() - 0.5D) + random.nextDouble();
			double positionY = (getY() - 0.5D) + random.nextDouble();
			double positionZ = (getZ() - 0.5D) + random.nextDouble();
			level().addParticle(Sparkle.SPARKLE_PARTICLE.get(), positionX, positionY, positionZ, 0, 0, 0);
		}
	}
}