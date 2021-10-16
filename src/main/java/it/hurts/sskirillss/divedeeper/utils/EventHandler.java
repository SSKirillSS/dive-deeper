package it.hurts.sskirillss.divedeeper.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {
    private static int getLevelDifference(World world, int level, int altitude) {
        return (level == -1 ? world.getSeaLevel() : level) - altitude;
    }

    @SubscribeEvent
    public static void onDigSpeedCalculation(PlayerEvent.BreakSpeed event) {
        PlayerEntity player = event.getPlayer();
        World world = player.getEntityWorld();

        if (DDConfig.DiggingModifier.DIMENSIONS_BLACKLIST.get().contains(world.getDimensionKey().getLocation().toString()))
            return;

        BlockPos pos = event.getPos();

        if (DDConfig.DiggingModifier.BLOCKS_BLACKLIST.get().contains(world.getBlockState(pos).getBlock().getRegistryName().toString()))
            return;

        int difference = getLevelDifference(world, DDConfig.DiggingModifier.SEA_LEVEL.get(), pos.getY());

        if (difference <= 0)
            return;

        event.setNewSpeed(event.getNewSpeed() * (1 - difference * DDConfig.DiggingModifier.MULTIPLIER.get().floatValue() * 0.15F));
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        Entity entity = event.getSource().getTrueSource();
        LivingEntity target = event.getEntityLiving();

        if (!(entity instanceof PlayerEntity) || target instanceof PlayerEntity)
            return;

        PlayerEntity player = (PlayerEntity) entity;
        World world = player.getEntityWorld();

        if (DDConfig.DealtDamageModifier.DIMENSIONS_BLACKLIST.get().contains(world.getDimensionKey().getLocation().toString())
                || DDConfig.DealtDamageModifier.ENTITIES_BLACKLIST.get().contains(target.getType().getRegistryName().toString()))
            return;

        int difference = getLevelDifference(world, DDConfig.DealtDamageModifier.SEA_LEVEL.get(), target.getPosition().getY());

        if (difference <= 0)
            return;

        event.setAmount(event.getAmount() * (1 - difference * DDConfig.DealtDamageModifier.MULTIPLIER.get().floatValue() * 0.15F));
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity source = event.getSource().getTrueSource();

        if (!(entity instanceof PlayerEntity) || source == null || source instanceof PlayerEntity)
            return;

        PlayerEntity player = (PlayerEntity) entity;
        World world = player.getEntityWorld();

        if (DDConfig.IncomingDamageModifier.DIMENSIONS_BLACKLIST.get().contains(world.getDimensionKey().getLocation().toString())
                || DDConfig.IncomingDamageModifier.ENTITIES_BLACKLIST.get().contains(source.getType().getRegistryName().toString()))
            return;

        int difference = getLevelDifference(world, DDConfig.IncomingDamageModifier.SEA_LEVEL.get(), player.getPosition().getY());

        if (difference <= 0)
            return;

        event.setAmount(event.getAmount() * (1 + difference * DDConfig.IncomingDamageModifier.MULTIPLIER.get().floatValue() * 0.15F));
    }

    @SubscribeEvent
    public static void onPlayerHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (!(entity instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) entity;
        World world = player.getEntityWorld();

        if (DDConfig.HealingModifier.DIMENSIONS_BLACKLIST.get().contains(world.getDimensionKey().getLocation().toString()))
            return;

        int difference = getLevelDifference(world, DDConfig.HealingModifier.SEA_LEVEL.get(), player.getPosition().getY());

        if (difference <= 0)
            return;

        event.setAmount(event.getAmount() * (1 - difference * DDConfig.HealingModifier.MULTIPLIER.get().floatValue() * 0.15F));
    }
}