package xyz.ultrapixelmon.pepefab;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.blocks.ranch.BlockRanchBlock;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityRanchBlock;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityBreeding;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemIsisHourglass;
import com.pixelmonmod.pixelmon.util.helpers.BlockHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class Listeners {

    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event){
        IBlockState state = event.getWorld().getBlockState(event.getPos());
        EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
        //final boolean hasPermission = PermissionAPI.hasPermission(player, "ranchdittodittohourglassdisable.bypass");
        if(state.getBlock() instanceof BlockRanchBlock){
            BlockRanchBlock ranchBlock = (BlockRanchBlock) state.getBlock();
            BlockPos loc = ranchBlock.findBaseBlock(event.getWorld(), new BlockPos.MutableBlockPos(event.getPos()), state);
            TileEntityRanchBlock ranch = BlockHelper.<TileEntityRanchBlock>getTileEntity(TileEntityRanchBlock.class, event.getWorld(), loc);
            if(ranch != null){
                if(ranch.canBreed()){
                    List<EntityBreeding> breeds = Lists.newArrayList(ranch.getEntities());
                        if(breeds.get(0).getSpecies().equals(EnumSpecies.Ditto) && breeds.get(1).getSpecies().equals(EnumSpecies.Ditto)){
                            if(!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof ItemIsisHourglass){
                                if(!player.canUseCommand(4,"ranchdittodittohourglassdisable.bypass")){
                                    event.setCanceled(true);
                                    player.sendMessage(new TextComponentString(TextFormatting.RED + Config.message));
                                }
                            }
                        }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteractSpecific event){
        Entity entity = event.getTarget();
        EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
        if(entity instanceof EntityBreeding){
            EntityBreeding pixelmon = (EntityBreeding) entity;
            Pokemon pokemon = pixelmon.getPokemonData();
            if(pokemon.isInRanch()){
                Map<BlockPos, TileEntityRanchBlock> map = BlockHelper
                        .findAllTileEntityWithinRange(TileEntityRanchBlock.class, pixelmon, 32, (tileEntity) -> {
                            TileEntityRanchBlock ranch = (TileEntityRanchBlock) tileEntity;
                            if (ranch.canBreed()) {
                                for (TileEntityRanchBlock.RanchPoke ranchPoke : ranch.getPokemonData()) {
                                    if (ranchPoke.matches(pokemon)) {
                                        return true;
                                    }
                                }
                            }
                            return false;
                        });
                List<TileEntityRanchBlock> ranches = Lists.newArrayList(map.values());
                if(ranches.size() == 1){
                    TileEntityRanchBlock ranch = ranches.get(0);
                    List<EntityBreeding> breeds = Lists.newArrayList(ranch.getEntities());
                    if(breeds.get(0).getSpecies().equals(EnumSpecies.Ditto) && breeds.get(1).getSpecies().equals(EnumSpecies.Ditto)){
                        if(!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof ItemIsisHourglass){
                            if(!player.canUseCommand(4,"ranchdittodittohourglassdisable.bypass")){
                                event.setCanceled(true);
                                event.setCancellationResult(EnumActionResult.SUCCESS);
                                player.sendMessage(new TextComponentString(TextFormatting.RED + Config.message));
                            }
                        }
                    }
                }
            }
        }
    }
}
