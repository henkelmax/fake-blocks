package de.maxhenkel.fakeblocks.proxy;

import de.maxhenkel.fakeblocks.Main;
import de.maxhenkel.fakeblocks.TileEntityFakeBlock;
import de.maxhenkel.fakeblocks.MBlocks;
import de.maxhenkel.fakeblocks.MItems;
import de.maxhenkel.fakeblocks.TileentitySpecialRendererFakeBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy{
 
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFakeBlock.class, new TileentitySpecialRendererFakeBlock());
		addRenderBlock(MBlocks.FAKE_BLOCK);
		addRenderItem(MItems.ITEM_FAKE_BLOCK);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
	private void addRenderItem(Item i){
		String name=i.getUnlocalizedName().replace("item.", "");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(Main.MODID +":" +name, "inventory"));
	}
	
	private void addRenderBlock(Block b){
		String name=b.getUnlocalizedName().replace("tile.", "");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(b), 0, new ModelResourceLocation(Main.MODID +":" +name, "inventory"));
	}

}
