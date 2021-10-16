package it.hurts.sskirillss.divedeeper.utils;

import com.google.common.collect.Lists;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collections;
import java.util.List;

public class DDConfig {
    public static ForgeConfigSpec DD_CONFIG;

    public static class DiggingModifier {
        public static ForgeConfigSpec.IntValue SEA_LEVEL;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSIONS_BLACKLIST;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCKS_BLACKLIST;
        public static ForgeConfigSpec.DoubleValue MULTIPLIER;

        private static void setup(ForgeConfigSpec.Builder builder) {
            builder.push("digging");

            SEA_LEVEL = builder.defineInRange("sea_level", -1, -1, Integer.MAX_VALUE);
            DIMENSIONS_BLACKLIST = builder.defineList("dimensions_blacklist", Collections.singletonList(Level.END.location().toString()), value -> value instanceof String);
            BLOCKS_BLACKLIST = builder.defineList("blocks_blacklist", Lists.newArrayList(), value -> value instanceof String);
            MULTIPLIER = builder.defineInRange("multiplier", 0.075, 0, 1);

            builder.pop();
        }
    }

    public static class DealtDamageModifier {
        public static ForgeConfigSpec.IntValue SEA_LEVEL;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSIONS_BLACKLIST;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITIES_BLACKLIST;
        public static ForgeConfigSpec.DoubleValue MULTIPLIER;

        private static void setup(ForgeConfigSpec.Builder builder) {
            builder.push("dealt damage");

            SEA_LEVEL = builder.defineInRange("sea_level", -1, -1, Integer.MAX_VALUE);
            DIMENSIONS_BLACKLIST = builder.defineList("dimensions_blacklist", Collections.singletonList(Level.END.location().toString()), value -> value instanceof String);
            ENTITIES_BLACKLIST = builder.defineList("entities_blacklist", Lists.newArrayList(), value -> value instanceof String);
            MULTIPLIER = builder.defineInRange("multiplier", 0.05, 0, 1);

            builder.pop();
        }
    }

    public static class IncomingDamageModifier {
        public static ForgeConfigSpec.IntValue SEA_LEVEL;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSIONS_BLACKLIST;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITIES_BLACKLIST;
        public static ForgeConfigSpec.DoubleValue MULTIPLIER;

        private static void setup(ForgeConfigSpec.Builder builder) {
            builder.push("incoming damage");

            SEA_LEVEL = builder.defineInRange("sea_level", -1, -1, Integer.MAX_VALUE);
            DIMENSIONS_BLACKLIST = builder.defineList("dimensions_blacklist", Collections.singletonList(Level.END.location().toString()), value -> value instanceof String);
            ENTITIES_BLACKLIST = builder.defineList("entities_blacklist", Lists.newArrayList(), value -> value instanceof String);
            MULTIPLIER = builder.defineInRange("multiplier", 0.065, 0, 1);

            builder.pop();
        }
    }

    public static class HealingModifier {
        public static ForgeConfigSpec.IntValue SEA_LEVEL;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSIONS_BLACKLIST;
        public static ForgeConfigSpec.DoubleValue MULTIPLIER;

        private static void setup(ForgeConfigSpec.Builder builder) {
            builder.push("healing");

            SEA_LEVEL = builder.defineInRange("sea_level", -1, -1, Integer.MAX_VALUE);
            DIMENSIONS_BLACKLIST = builder.defineList("dimensions_blacklist", Collections.singletonList(Level.END.location().toString()), value -> value instanceof String);
            MULTIPLIER = builder.defineInRange("multiplier", 0.075, 0, 1);

            builder.pop();
        }
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        DiggingModifier.setup(builder);
        DealtDamageModifier.setup(builder);
        IncomingDamageModifier.setup(builder);
        HealingModifier.setup(builder);

        DD_CONFIG = builder.build();
    }
}