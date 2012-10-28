/**
 * WitherBlocker - Package: syam.witherblocker.listener
 * Created: 2012/10/28 15:58:23
 */
package syam.witherblocker.listener;

import java.util.logging.Logger;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import syam.witherblocker.ConfigurationManager;
import syam.witherblocker.WitherBlocker;

/**
 * EntityListener (EntityListener.java)
 * @author syam(syamn)
 */
public class EntityListener implements Listener{
    private final static Logger log = WitherBlocker.log;
    private final static String logPrefix = WitherBlocker.logPrefix;
    private final static String msgPrefix = WitherBlocker.msgPrefix;

    private final WitherBlocker plugin;
    private final ConfigurationManager configs;

    public EntityListener(final WitherBlocker instance){
        this.plugin = instance;
        this.configs = instance.getConfigs();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawn(final CreatureSpawnEvent event){
        if (!event.getEntityType().equals(EntityType.WITHER)){
            return;
        }

        plugin.debug("Caught Wither spawning!");

        if (plugin.getConfigs().getWhiteWorlds().isEmpty()){
            plugin.debug("WhiteWorlds is Empty! Cancelling CreatureSpawnEvent!");
            event.setCancelled(true);
        }

        if (!plugin.getConfigs().getWhiteWorlds().contains(event.getLocation().getWorld().getName())){
            plugin.debug("World " + event.getLocation().getWorld().getName() + " is not contains WhiteWorlds! Cancelling CreatureSpawnEvent!");
            event.setCancelled(true);
        }
    }
}
