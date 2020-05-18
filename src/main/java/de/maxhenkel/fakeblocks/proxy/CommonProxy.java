package de.maxhenkel.fakeblocks.proxy;

import de.maxhenkel.fakeblocks.MBlocks;
import de.maxhenkel.fakeblocks.MItems;
import de.maxhenkel.fakeblocks.TileEntityFakeBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

public class CommonProxy {

	public void preinit(FMLPreInitializationEvent event) {
		
	}

	public void init(FMLInitializationEvent event) {
		registerOnlyBlock(MBlocks.FAKE_BLOCK);
		registerItem(MItems.ITEM_FAKE_BLOCK);
		
		GameRegistry.registerTileEntity(TileEntityFakeBlock.class, "TileEntityFakeBlock");

		GameRegistry.addRecipe(new ItemStack(MItems.ITEM_FAKE_BLOCK), new Object[] { "XXX", "XSX", "XXX", Character.valueOf('X'), Items.DYE, Character.valueOf('S'), Blocks.STONE });

		GameRegistry.addRecipe(new ItemStack(MItems.ITEM_FAKE_BLOCK), new Object[] { "XXX", "XSX", "XXX", Character.valueOf('X'), Items.DYE, Character.valueOf('S'), Blocks.CLAY });

	}

	public void postinit(FMLPostInitializationEvent event) {

	}

	private void registerItem(Item i) {
		GameRegistry.register(i);
	}

	private void registerBlock(Block b) {
		GameRegistry.register(b);
		GameRegistry.register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
	}

	private void registerOnlyBlock(Block b) {
		GameRegistry.register(b);
	}
	
}
