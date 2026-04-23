package garden.hestia.omikuji;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Omikuji implements ModInitializer {
	public static final String ID = "omikuji";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(ID, path);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("[omikuji] Good Fortune!");
		OmikujiBlocks.initialize();
		OmikujiItems.initialize();
	}
}
