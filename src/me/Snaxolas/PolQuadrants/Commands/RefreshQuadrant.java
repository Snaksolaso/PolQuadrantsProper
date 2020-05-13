package me.Snaxolas.PolQuadrants.Commands;

import me.Snaxolas.PolQuadrants.AutoUpdate;
import me.Snaxolas.PolQuadrants.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RefreshQuadrant implements CommandExecutor {
    static Main plugin;

    public RefreshQuadrant(Main main) {
        plugin = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("RefreshQuadrant") || label.equalsIgnoreCase("refreshQ")){
            if(!(sender.hasPermission("PolQuadrants.refresh"))){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                return true;
            }
            if(args.length != 0 && Bukkit.getPlayer(args[0]) != null){
                //refreshing another player -- probably most common application.

                if(!(sender.hasPermission("PolQuadrants.refresh.others"))){
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission!");
                    return true;
                }

                //checks if person's nick should not be changed e.g. an admin with a nickname prefix.
                if(Bukkit.getPlayer(args[0]).hasPermission("PolQuadrants.doNotUpdateNick")){
                    sender.sendMessage(ChatColor.DARK_RED + "This player's nickname cannot be updated, as they have the \"PolQuadrants.doNotUpdateNick\" permission.");
                    return true;
                }
                AutoUpdate.AutoUpdate(Bukkit.getPlayer(args[0]), plugin);
                sender.sendMessage(ChatColor.DARK_GREEN + "Successfully refreshed " + Bukkit.getPlayer(args[0]).getName() + "'s nickname");
                return true;
            }
            if(sender instanceof Player){
                //refreshing own nick
                Player p = (Player) sender;

                //checks if person's nick should not be changed e.g. an admin with a nickname prefix.
                if(p.hasPermission("PolQuadrants.doNotUpdateNick")){
                    sender.sendMessage(ChatColor.DARK_RED + "Your nickname cannot be updated, as you have the \"doNotUpdateNick\" permission.");
                    return true;
                }
                AutoUpdate.AutoUpdate((Player) sender, plugin);
                return true;
            }
        }

            return false;
    }
}
