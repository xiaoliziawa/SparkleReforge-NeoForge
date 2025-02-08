package miyucomics.sparkle.mixin;

import miyucomics.sparkle.Sparkle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	@Shadow public abstract ItemStack getItem();

	public ItemEntityMixin(EntityType<ItemEntity> type, Level level) {
		super(type, level);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void addParticles(CallbackInfo info) {
		if (!level().isClientSide())
			return;
		if (Sparkle.SPARKLY_ITEMS.contains(getItem().getItem()) && random.nextFloat() < 0.1F) {
			double positionX = (getX() - 0.5D) + random.nextDouble();
			double positionY = (getY() - 0.5D) + random.nextDouble();
			double positionZ = (getZ() - 0.5D) + random.nextDouble();
			level().addParticle(Sparkle.SPARKLE_PARTICLE.get(), positionX, positionY, positionZ, 0, 0, 0);
		}
	}
}