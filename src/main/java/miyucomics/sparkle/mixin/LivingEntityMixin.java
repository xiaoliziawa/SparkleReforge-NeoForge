package miyucomics.sparkle.mixin;

import miyucomics.sparkle.Sparkle;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void addParticles(CallbackInfo ci) {
		if (!level().isClientSide())
			return;
		var client = Minecraft.getInstance();
		if (isAlive() && client.player != null) {
			if (client.player.getUUID().equals(getUUID()) && client.options.getCameraType().isFirstPerson())
				return;
			spawnSparkles(getShineValue((LivingEntity) (Object) this));
		}
	}

	@Unique
	private void spawnSparkles(int shineValue) {
		if (shineValue > 0) {
			if (this.random.nextInt(20 - shineValue) == 0) {
				double x = random.nextFloat() * 2 - 1;
				double y = random.nextFloat();
				double z = random.nextFloat() * 2 - 1;
				level().addParticle(Sparkle.SPARKLE_PARTICLE.get(), this.getX() + x, this.getY() + y + 1, this.getZ() + z, 0, 0, 0);
			}
		}
	}

	@Unique
	private static int getShineValue(LivingEntity entity) {
		int shineValue = 0;
		for (ItemStack stack : entity.getArmorSlots())
			if (Sparkle.SPARKLY_ITEMS.contains(stack.getItem()))
				shineValue++;
		return shineValue;
	}
}