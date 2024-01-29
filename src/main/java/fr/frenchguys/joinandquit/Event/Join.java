package fr.frenchguys.joinandquit.Event;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import fr.frenchguys.joinandquit.JoinAndQuit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    private JoinAndQuit main;

    public Join(JoinAndQuit main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if sending popup is enabled in the configuration
        if (main.getConfig().getBoolean("Join.joinPop")) {
            String message = main.getConfig().getString("Join.joinMessage");
            if (message != null) {
                // Replace  %player% with player's name
                message = message.replace("%player%", player.getName());

                // Replace color codes with Minecraft color codes
                message = message.replaceAll("&", "§");

                int duration = main.getConfig().getInt("Join.duration", 30);

                // Send the ActionBar message to the player
                ActionBarAPI.sendActionBarToAllPlayers(message, duration);
            } else {
                main.getLogger().warning("Join message not found in configuration.");
            }
        }

        // Check if using default join message from Minecraft is disabled
        if (!main.getConfig().getBoolean("Default.DefaultJoin")) {
            // Cancel the default join message
            event.setJoinMessage(null);
        }
    }
}
