package io.garden.hestia.omikuji;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Omikuji implements ModInitializer {
	public static final String ID = "omikuji";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		//LOGGER.info("[omikuji] Good Fortune!");
	}
}
