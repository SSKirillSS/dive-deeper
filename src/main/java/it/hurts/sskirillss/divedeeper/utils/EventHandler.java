package it.hurts.sskirillss.divedeeper.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {
    private static int getLevelDifference(Level world, int level, int altitude) {
        return (level == -1 ? world.getSeaLevel() : level) - altitude;
    }

    @SubscribeEvent
    public static void onDigSpeedCalculation(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        Level world = player.getCommandSenderWorld();

        if (DDConfig.DiggingModifier.DIMENSIONS_BLACKLIST.get().contains(world.dimension().location().toString()))
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
        Entity entity = event.getSource().getEntity();
        LivingEntity target = event.getEntityLiving();

        if (!(entity instanceof Player) || target instanceof Player)
            return;

        Player player = (Player) entity;
        Level world = player.getCommandSenderWorld();

        if (DDConfig.DealtDamageModifier.DIMENSIONS_BLACKLIST.get().contains(world.dimension().location().toString())
                || DDConfig.DealtDamageModifier.ENTITIES_BLACKLIST.get().contains(target.getType().getRegistryName().toString()))
            return;

        int difference = getLevelDifference(world, DDConfig.DealtDamageModifier.SEA_LEVEL.get(), target.blockPosition().getY());

        if (difference <= 0)
            return;

        event.setAmount(event.getAmount() * (1 - difference * DDConfig.DealtDamageModifier.MULTIPLIER.get().floatValue() * 0.15F));
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity source = event.getSource().getEntity();

        if (!(entity instanceof Player) || source == null || source instanceof Player)
            return;

        Player player = (Player) entity;
        Level world = player.getCommandSenderWorld();

        if (DDConfig.IncomingDamageModifier.DIMENSIONS_BLACKLIST.get().contains(world.dimension().location().toString())
                || DDConfig.IncomingDamageModifier.ENTITIES_BLACKLIST.get().contains(source.getType().getRegistryName().toString()))
            return;

        int difference = getLevelDifference(world, DDConfig.IncomingDamageModifier.SEA_LEVEL.get(), player.blockPosition().getY());

        if (difference <= 0)
            return;

        event.setAmount(event.getAmount() * (1 + difference * DDConfig.IncomingDamageModifier.MULTIPLIER.get().floatValue() * 0.15F));
    }

    @SubscribeEvent
    public static void onPlayerHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        Level world = player.getCommandSenderWorld();

        if (DDConfig.HealingModifier.DIMENSIONS_BLACKLIST.get().contains(world.dimension().location().toString()))
            return;

        int difference = getLevelDifference(world, DDConfig.HealingModifier.SEA_LEVEL.get(), player.blockPosition().getY());

        if (difference <= 0)
            return;

        event.setAmount(event.getAmount() * (1 - difference * DDConfig.HealingModifier.MULTIPLIER.get().floatValue() * 0.15F));
    }
}