package fr.frenchguys.joinandquit.Event;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import fr.frenchguys.joinandquit.JoinAndQuit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {

    private JoinAndQuit main;

    public Quit(JoinAndQuit main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Check if sending popup is enabled in the configuration
        if (main.getConfig().getBoolean("Leave.LeavePop")) {
            String message = main.getConfig().getString("Leave.LeaveMessage");
            if (message != null) {
                // Replace %player% with player's name
                message = message.replace("%player%", player.getName());

                // Replace color codes with Minecraft color codes
                message = message.replaceAll("&", "§");

                int duration = main.getConfig().getInt("Leave.duration", 30);

                // Send the ActionBar message to the player
                ActionBarAPI.sendActionBarToAllPlayers(message, duration);
            } else {
                main.getLogger().warning("Leave message not found in configuration.");
            }
        }

        // Check if using default leave message from Minecraft is disabled
        if (!main.getConfig().getBoolean("Default.DefaultLeave")) {
            // Cancel the default leave message
            event.setQuitMessage(null);
        }
    }
}
