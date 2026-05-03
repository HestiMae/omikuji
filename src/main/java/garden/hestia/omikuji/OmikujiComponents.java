package garden.hestia.omikuji;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;

public class OmikujiComponents {
	public static final DataComponentType<Boolean> OPENED = Registry.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		Omikuji.id("opened"),
		DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build()
	);
	public static final DataComponentType<FortuneComponent> FORTUNE_COMPONENT = Registry.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		Omikuji.id("fortune"),
		DataComponentType.<FortuneComponent>builder().persistent(FortuneComponent.FORTUNE_COMPONENT_CODEC).build()
	);
	public static void initialize() {
		ItemComponentTooltipProviderRegistry.addAfter(DataComponents.DAMAGE, OmikujiComponents.FORTUNE_COMPONENT);
	}
}
