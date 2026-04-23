package garden.hestia.omikuji;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class OmikujiBlocks {
	public static final OmikujiBoxBlock OMIKUJI_BOX = register("omikuji_box", OmikujiBoxBlock::new, BlockBehaviour.Properties.of(), true);
	public static void initialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register((creativeTab) -> {
			creativeTab.accept(OMIKUJI_BOX.asItem());
		});
	}

	private static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem)
	{
		ResourceKey<Item> itemKey = keyOfItem(name);
		ResourceKey<Block> blockKey = keyOfBlock(name);
		T block = blockFactory.apply(settings.setId(blockKey));
		if (shouldRegisterItem)
		{
			BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
			Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
		}
		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}
	private static ResourceKey<Block> keyOfBlock(String name) {
		return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Omikuji.ID, name));
	}

	private static ResourceKey<Item> keyOfItem(String name) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Omikuji.ID, name));
	}
}
