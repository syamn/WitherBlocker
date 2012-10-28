/**
 * WitherBlocker - Package: syam.witherblocker
 * Created: 2012/10/28 15:40:22
 */
package syam.witherblocker;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import syam.witherblocker.listener.EntityListener;
import syam.witherblocker.util.Metrics;

/**
 * WitherBlocker (WitherBlocker.java)
 * @author syam(syamn)
 */
public class WitherBlocker extends JavaPlugin{
    // ** Logger **
    public final static Logger log = Logger.getLogger("Minecraft");
    public final static String logPrefix = "[WitherBlocker] ";
    public final static String msgPrefix = "&6[WitherBlocker] &f";

    // ** Listener **
    private final EntityListener entityListener = new EntityListener(this);

    // ** Classes **
    private ConfigurationManager config;

    // ** Instance **
    private static WitherBlocker instance;

    /**
     * Enabling plugin
     */
    public void onEnable(){
        instance  = this;
        config = new ConfigurationManager(this);
        PluginManager pm = getServer().getPluginManager();

        // 設定読み込み
        try{
            config.loadConfig(true);
        }catch(Exception ex){
            log.warning(logPrefix+ "an error occured while trying to load the config file.");
            ex.printStackTrace();
        }

        // Setup Listeners
        pm.registerEvents(entityListener, this);

        // メッセージ表示
        PluginDescriptionFile pdfFile=this.getDescription();
        log.info("["+pdfFile.getName()+"] version "+pdfFile.getVersion()+" is enabled!");

        setupMetrics(); // mcstats
    }

    /**
     * Disabling plugin
     */
    public void onDisable(){
        // メッセージ表示
        PluginDescriptionFile pdfFile=this.getDescription();
        log.info("["+pdfFile.getName()+"] version "+pdfFile.getVersion()+" is disabled!");
    }

    /**
     * Setup Metrics
     */
    private void setupMetrics(){
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ex) {
            log.warning(logPrefix+"cant send metrics data!");
            ex.printStackTrace();
        }
    }

    public void debug(final String msg){
        if (getConfigs().isDebug()){
            log.info(logPrefix+"[Debug] " + msg);
        }
    }

    /**
     * Return ConfigurationManager
     * @return ConfigurationManager
     */
    public ConfigurationManager getConfigs(){
        return config;
    }

    /**
     * Return plugin instance
     * @return WitherBlocker instance
     */
    public static WitherBlocker getInstance(){
        return instance;
    }
}
