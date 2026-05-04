package garden.hestia.omikuji;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.function.Consumer;

public record FortuneComponent(String poetry) implements TooltipProvider {
	public static final Codec<FortuneComponent> FORTUNE_COMPONENT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
		Codec.STRING.fieldOf("poetry").forGetter(FortuneComponent::poetry)
	).apply(builder, FortuneComponent::new));

	public record Fortune(Component fortune, int lines, Consumer<Player> effect) {
	}

	public static final Map<String, Fortune> fortunes = Map.of(
		"leaves", new Fortune(Component.literal("半吉").withStyle(ChatFormatting.YELLOW), 3, p -> p.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600))),
		"cattle", new Fortune(Component.literal("小吉").withStyle(ChatFormatting.AQUA), 3, p -> p.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600))),
		"bell", new Fortune(Component.literal("凶").withStyle(ChatFormatting.DARK_RED), 3, p -> p.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 1800))),
		"hearts", new Fortune(Component.literal("凶").withStyle(ChatFormatting.DARK_RED), 3, p -> p.addDeltaMovement(new Vec3(0, 10, 0))),
		"chopsticks", new Fortune(Component.literal("小吉").withStyle(ChatFormatting.AQUA), 3, FortuneComponent::shuffle),
		"offer", new Fortune(Component.literal("末吉").withStyle(ChatFormatting.LIGHT_PURPLE), 3, p -> p.addItem(Items.TOTEM_OF_UNDYING.getDefaultInstance().copy()))
	);

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
		if (Boolean.TRUE.equals(components.get(OmikujiComponents.OPENED))) {
			Fortune fortune = fortunes.getOrDefault(poetry, fortunes.get("cattle"));
			consumer.accept(fortune.fortune);
			for (int i = 0; i < fortune.lines; i++) {
				consumer.accept(Component.translatable("poetry.omikuji.%s.%d".formatted(poetry, i)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
			}
		}
	}

	public static void FortuneEffectHelper(ItemStack stack, Player player) {
		FortuneComponent fc = stack.get(OmikujiComponents.FORTUNE_COMPONENT);
		if (fc != null) {
			Fortune fortune = fortunes.get(fc.poetry);
			if (fortune != null) {
				player.sendOverlayMessage(Component.translatable("poetry.omikuji.%s.%d".formatted(fc.poetry, 0)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY).append(Component.literal("...")));
				fortune.effect.accept(player);
			}
		}
	}

	public static void trySwap(Inventory inventory, int source, int target) {
		ItemStack sourceStack = inventory.getItem(source);
		ItemStack targetItemStack = inventory.getItem(target);
		if (sourceStack.isEmpty() || targetItemStack.isEmpty()) return;
		if (!inventory.canPlaceItem(target, sourceStack) || !inventory.canPlaceItem(source, targetItemStack)) return;
		inventory.setItem(source, targetItemStack);
		inventory.setItem(target, sourceStack);
	}

	public static void shuffle(Player player)
	{
		Inventory inv = player.getInventory();
		for (int i = 0; i < 100; i++) {
			trySwap(inv, player.getRandom().nextInt(inv.getNonEquipmentItems().size()), player.getRandom().nextInt(inv.getNonEquipmentItems().size()));
		}
	}
}
