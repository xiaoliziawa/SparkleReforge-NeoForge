package miyucomics.sparkle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

@Mod(Sparkle.MOD_ID)
@EventBusSubscriber(modid = Sparkle.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Sparkle {
    public static final String MOD_ID = "sparkle";

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MOD_ID);

    public static final Supplier<SimpleParticleType> SPARKLE_PARTICLE = PARTICLE_TYPES.register("sparkle",
            () -> new SimpleParticleType(true));

    public static SparkleConfig CONFIG = SparkleConfig.of(MOD_ID);

    public static List<Block> SPARKLY_BLOCKS;
    public static List<Item> SPARKLY_ITEMS;
    public static List<? extends EntityType<?>> SPARKLY_ENTITIES;

    public Sparkle(IEventBus modEventBus) {
        PARTICLE_TYPES.register(modEventBus);  // 添加这一行来注册粒子

        SPARKLY_BLOCKS = CONFIG.blocks.stream()
                .filter(string -> string != null && !string.isEmpty())
                .map(thing -> BuiltInRegistries.BLOCK.get(ResourceLocation.parse(thing)))
                .toList();

        SPARKLY_ITEMS = CONFIG.items.stream()
                .filter(string -> string != null && !string.isEmpty())
                .map(thing -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(thing)))
                .toList();

        SPARKLY_ENTITIES = CONFIG.entities.stream()
                .filter(string -> string != null && !string.isEmpty())
                .map(thing -> BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(thing)))
                .toList();
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SPARKLE_PARTICLE.get(), SparkleParticle.Factory::new);
    }
}