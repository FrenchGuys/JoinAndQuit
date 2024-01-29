package fr.frenchguys.joinandquit.Event;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import fr.frenchguys.joinandquit.JoinAndQuit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoin implements Listener {

    private JoinAndQuit main;

    public FirstJoin(JoinAndQuit main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if sending popup is enabled in the configuration
        if (main.getConfig().getBoolean("FirstJoin.FirstJoinPop")) {
            // Check if the player has joined before
            if (!player.hasPlayedBefore()) {
                // Check if a specific message is configured for the joining player
                String playerMessage = main.getConfig().getString("FirstJoin.PlayerMessage");
                if (playerMessage != null) {
                    // Replace %player% with player's name
                    playerMessage = playerMessage.replace("%player%", player.getName());

                    // Replace color codes with Minecraft color codes
                    playerMessage = playerMessage.replaceAll("&", "§");

                    int duration = main.getConfig().getInt("FirstJoin.duration", 50);

                    // Send the player-specific message only to the joining player
                    ActionBarAPI.sendActionBar(player, playerMessage, duration);
                } else {
                    main.getLogger().warning("Player-specific FirstJoin message not found in configuration.");
                }
            }
        }
    }
}
