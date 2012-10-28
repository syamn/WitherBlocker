/**
 * WitherBlocker - Package: syam.witherblocker.listener
 * Created: 2012/10/28 17:12:58
 */
package syam.witherblocker.listener;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import syam.witherblocker.ConfigurationManager;
import syam.witherblocker.WitherBlocker;
import syam.witherblocker.permission.Perms;

/**
 * BlockListener (BlockListener.java)
 * @author syam(syamn)
 */
public class BlockListener implements Listener{
    private final static Logger log = WitherBlocker.log;
    private final static String logPrefix = WitherBlocker.logPrefix;
    private final static String msgPrefix = WitherBlocker.msgPrefix;

    private final WitherBlocker plugin;
    private final ConfigurationManager configs;

    public BlockListener(final WitherBlocker instance){
        this.plugin = instance;
        this.configs = instance.getConfigs();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockCanBuildEvent(final BlockCanBuildEvent event){
        if (!isTriggeredBlock(event.getBlock())){
            return;
        }

        if (event.isBuildable() && !isPlaceable(event.getBlock())){
            event.setBuildable(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlaceEvent(final BlockPlaceEvent event){
        if (!isTriggeredBlock(event.getBlock())){
            return;
        }

        if (!isPlaceable(event.getPlayer(), event.getBlock(), event.canBuild())){
            event.setBuild(false);
            event.setCancelled(true);
        }
    }

    private boolean isTriggeredBlock(final Block block){
        if (block.getTypeId() == 144) {
            plugin.debug("This is a Triggered Block!");
            return true;
        }
        return false;
    }

    private boolean isPlaceable(final Player player, final Block block, final boolean canbuild){
        if (player != null && Perms.ALLOW.has(player)){
            return true;
        }
        if (player != null && !canbuild){
            return false;
        }

        /*
        if (!block.getRelative(BlockFace.DOWN).getType().equals(Material.SOUL_SAND)){
            return true;
        }
        */

        // check
        final BlockFace[] directions = new BlockFace[]{
                BlockFace.NORTH,
                BlockFace.EAST,
                BlockFace.SOUTH,
                BlockFace.WEST
            };
        Block check = null;
        for (BlockFace face : directions){
            check = block.getRelative(face);
            if ((isTriggeredBlock(check) && check.getRelative(BlockFace.DOWN).equals(Material.SOUL_SAND))
                    || isTriggeredBlock(check.getRelative(face))){
                return false;
            }
        }

        return true;
    }

    private boolean isPlaceable(final Block block){
        return isPlaceable(null, block, false);
    }
}
