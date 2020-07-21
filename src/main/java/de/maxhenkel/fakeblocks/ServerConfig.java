package de.maxhenkel.fakeblocks;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServerConfig extends ConfigBase {

    private final ForgeConfigSpec.ConfigValue<List<? extends String>> fakeBlockBlacklistSpec;

    public List<Block> fakeBlockBlacklist;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        fakeBlockBlacklistSpec = builder
                .comment("The blocks that should not be able to be put into the fake block")
                .defineList("fake_block_blacklist", Arrays.asList(
                        "minecraft:bedrock"
                ), Objects::nonNull);
    }

    @Override
    public void onReload(ModConfig.ModConfigEvent event) {
        super.onReload(event);
        fakeBlockBlacklist = fakeBlockBlacklistSpec.get().stream().map(s -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s))).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
