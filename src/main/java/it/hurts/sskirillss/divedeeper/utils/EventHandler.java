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
    public static int getLevelDifference(World world, int altitude) {
        return (DDConfig.SEA_LEVEL.get() == -1 ? world.getSeaLevel() : DDConfig.SEA_LEVEL.get()) - altitude;
    }

    @SubscribeEvent
    public static void onDigSpeedCalculation(PlayerEvent.BreakSpeed event) {
        PlayerEntity player = event.getPlayer();
        World world = player.getEntityWorld();
        BlockPos pos = event.getPos();
        if (DDConfig.DIG_SPEED_BLOCKS_BLACKLIST.get().contains(world.getBlockState(pos).getBlock().getRegistryName().toString())) return;
        int difference = getLevelDifference(world, pos.getY());
        if (difference <= 0) return;
        event.setNewSpeed(event.getNewSpeed() * (1 - difference * DDConfig.DIG_SPEED_MULTIPLIER.get().floatValue() * 0.15F));
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        Entity entity = event.getSource().getTrueSource();
        LivingEntity target = event.getEntityLiving();
        if (!(entity instanceof PlayerEntity) || target instanceof PlayerEntity) return;
        PlayerEntity player = (PlayerEntity) entity;
        if (DDConfig.DEALT_DAMAGE_ENTITIES_BLACKLIST.get().contains(target.getType().getRegistryName().toString())) return;
        int difference = getLevelDifference(player.getEntityWorld(), target.getPosition().getY());
        if (difference <= 0) return;
        event.setAmount(event.getAmount() * (1 - difference * DDConfig.DEALT_DAMAGE_MULTIPLIER.get().floatValue() * 0.15F));
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity source = event.getSource().getTrueSource();
        if (!(entity instanceof PlayerEntity) || source == null || source instanceof PlayerEntity) return;
        PlayerEntity player = (PlayerEntity) entity;
        if (DDConfig.INCOMING_DAMAGE_ENTITIES_BLACKLIST.get().contains(source.getType().getRegistryName().toString())) return;
        int difference = getLevelDifference(player.getEntityWorld(), player.getPosition().getY());
        if (difference <= 0) return;
        event.setAmount(event.getAmount() * (1 + difference * DDConfig.INCOMING_DAMAGE_MULTIPLIER.get().floatValue() * 0.15F));
    }

    @SubscribeEvent
    public static void onPlayerHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!(entity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) entity;
        int difference = getLevelDifference(player.getEntityWorld(), player.getPosition().getY());
        if (difference <= 0) return;
        event.setAmount(event.getAmount() * (1 - difference * DDConfig.HEALING_MULTIPLIER.get().floatValue() * 0.15F));
    }
}