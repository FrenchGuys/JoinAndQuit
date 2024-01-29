package fr.frenchguys.joinandquit;

import fr.frenchguys.joinandquit.Event.FirstJoin;
import fr.frenchguys.joinandquit.Event.Join;
import fr.frenchguys.joinandquit.Event.Quit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class JoinAndQuit extends JavaPlugin {

    @Override
    public void onEnable() {
        Plugin desiredPlugin = getServer().getPluginManager().getPlugin("ActionBarAPI");

        reloadCustomConfig();

        if (desiredPlugin != null) {
            getLogger().info("--------------------------");
            getLogger().info("ActionBarAPI plugin found, activating...");
            getLogger().info("--------------------------");
            // Activate the desired plugin
            getServer().getPluginManager().enablePlugin(desiredPlugin);
        } else {
            getLogger().warning("--------------------------");
            getLogger().warning("ActionBarAPI plugin not found, deactivating...");
            getLogger().warning("--------------------------");
        }

        // Enregistrer l'événement de join seulement si la configuration le permet
            getServer().getPluginManager().registerEvents(new Join(this), this);
        getServer().getPluginManager().registerEvents(new Quit(this), this);
        getServer().getPluginManager().registerEvents(new FirstJoin(this), this);


    }

    public void reloadCustomConfig() {
        reloadConfig();

        File customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            saveResource("config.yml", false);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
