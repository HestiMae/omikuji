package garden.hestia.omikuji;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Omikuji implements ModInitializer {
	public static final String ID = "omikuji";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(ID, path);
	}
	public static TagKey<Item> OFFERINGS = TagKey.create(Registries.ITEM, id("offerings"));
	public static ResourceKey<LootTable> BOX_REWARDS = ResourceKey.create(Registries.LOOT_TABLE, id("offering/omikuji_box"));

	@Override
	public void onInitialize() {
		LOGGER.info("[omikuji] Good Fortune!");
		OmikujiBlocks.initialize();
		OmikujiItems.initialize();
		OmikujiComponents.initialize();
	}
}
