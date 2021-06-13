package it.hurts.sskirillss.divedeeper.utils;

import com.google.common.collect.Lists;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collections;
import java.util.List;

public class DDConfig {
    public static ForgeConfigSpec DD_CONFIG;

    public static ForgeConfigSpec.IntValue SEA_LEVEL;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSIONS_BLACKLIST;
    public static ForgeConfigSpec.DoubleValue DIG_SPEED_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIG_SPEED_BLOCKS_BLACKLIST;
    public static ForgeConfigSpec.DoubleValue DEALT_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DEALT_DAMAGE_ENTITIES_BLACKLIST;
    public static ForgeConfigSpec.DoubleValue INCOMING_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> INCOMING_DAMAGE_ENTITIES_BLACKLIST;
    public static ForgeConfigSpec.DoubleValue HEALING_MULTIPLIER;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        SEA_LEVEL = builder.defineInRange("sea_level", -1, -1, Integer.MAX_VALUE);
        DIMENSIONS_BLACKLIST = builder.defineList("dimensions_blacklist", Collections.singletonList(World.THE_END.getLocation().toString()), value -> value instanceof String);
        DIG_SPEED_MULTIPLIER = builder.defineInRange("dig_speed_multiplier", 0.075, 0, 1);
        DIG_SPEED_BLOCKS_BLACKLIST = builder.defineList("dig_speed_blocks_blacklist", Lists.newArrayList(), value -> value instanceof String);
        DEALT_DAMAGE_MULTIPLIER = builder.defineInRange("dealt_damage_multiplier", 0.05, 0, 1);
        DEALT_DAMAGE_ENTITIES_BLACKLIST = builder.defineList("dealt_damage_entities_blacklist", Lists.newArrayList(), value -> value instanceof String);
        INCOMING_DAMAGE_MULTIPLIER = builder.defineInRange("incoming_damage_multiplier", 0.065, 0, 1);
        INCOMING_DAMAGE_ENTITIES_BLACKLIST = builder.defineList("incoming_damage_entities_blacklist", Lists.newArrayList(), value -> value instanceof String);
        HEALING_MULTIPLIER = builder.defineInRange("healing_multiplier", 0.075, 0, 1);

        DD_CONFIG = builder.build();
    }
}