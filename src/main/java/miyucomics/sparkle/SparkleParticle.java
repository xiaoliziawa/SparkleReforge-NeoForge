package miyucomics.sparkle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;

public class SparkleParticle extends TextureSheetParticle {
	private final SpriteSet spriteSet;

	protected SparkleParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet sprites) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.spriteSet = sprites;
		this.lifetime = 20;
		this.xd = 0;
		this.yd = 0;
		this.zd = 0;
		this.setSpriteFromAge(sprites);
		this.scale(2.5F);
	}

	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.spriteSet);
	}

	@Override
	public int getLightColor(float partialTick) {
		int i = super.getLightColor(partialTick);
		int k = i >> 16 & 0xFF;
		return 240 | k << 16;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Factory(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public SparkleParticle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			SparkleParticle sparkleParticle = new SparkleParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
			sparkleParticle.setAlpha(1.0f);
			return sparkleParticle;
		}
	}
}