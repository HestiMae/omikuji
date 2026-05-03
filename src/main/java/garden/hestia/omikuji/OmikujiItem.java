package garden.hestia.omikuji;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OmikujiItem extends Item {
	public OmikujiItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stackInHand = player.getItemInHand(hand);
		if (!Boolean.TRUE.equals(stackInHand.get(OmikujiComponents.OPENED)))
		{
			stackInHand.set(OmikujiComponents.OPENED, true);
			player.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0f, 1.36f);
			FortuneComponent.FortuneEffectHelper(stackInHand, player);
			return InteractionResult.SUCCESS;
		}
		return super.use(level, player, hand);
	}
}
