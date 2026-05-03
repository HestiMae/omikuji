package garden.hestia.omikuji;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class OmikujiBoxBlock extends Block {
	public OmikujiBoxBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (itemStack.is(Omikuji.OFFERINGS))
		{
			if (level instanceof ServerLevel sl) dropFromBlockInteractLootTable(sl, Omikuji.BOX_REWARDS, state, null, itemStack, player, (serverLevel, stack) -> popResource(serverLevel, pos, stack));
			player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
			itemStack.consume(1, player);
			level.playSound(player, pos, SoundEvents.DECORATED_POT_INSERT, SoundSource.BLOCKS, 1.0f, 1.36f);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
	}
}
