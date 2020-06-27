package de.maxhenkel.fakeblocks;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Config {

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPairServer = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPairServer.getRight();
        SERVER = specPairServer.getLeft();

        Pair<ClientConfig, ForgeConfigSpec> specPairClient = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPairClient.getRight();
        CLIENT = specPairClient.getLeft();
    }

    public static class ServerConfig {

        private static ForgeConfigSpec.ConfigValue<List<? extends String>> FAKE_BLOCK_BLACKLIST;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            FAKE_BLOCK_BLACKLIST = builder
                    .comment("The blocks that should not be able to be put into the fake block")
                    .defineList("fake_block_blacklist", Arrays.asList(
                            "minecraft:bedrock"
                    ), Objects::nonNull);
        }

        public List<Block> getFakeBlockBlacklist() {
            return FAKE_BLOCK_BLACKLIST.get().stream().map(Config::getBlock).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public static class ClientConfig {

        public ClientConfig(ForgeConfigSpec.Builder builder) {

        }
    }

    @Nullable
    private static Block getBlock(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
    }

}
