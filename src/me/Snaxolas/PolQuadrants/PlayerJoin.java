package me.Snaxolas.PolQuadrants;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
public class PlayerJoin implements Listener {

    static Main plugin;

    public PlayerJoin(Main main) {
        this.plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        AutoUpdate.AutoUpdate(p, plugin);
    }
}
