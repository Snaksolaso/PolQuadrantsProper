package me.Snaxolas.PolQuadrants;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.io.File;

public class AutoUpdate {

    public static boolean MessageUnflaired(Player p){
        p.sendMessage(ChatColor.DARK_RED + "You do not currently have a quadrant flair.");
        p.sendMessage(ChatColor.GOLD + "Do /setquadrant <QuadrantName> to set your flair and chat color. " +
                "e.g. \"/setquadrant Centrist\" would change your flair and chat color accordingly.");

        return true;
    }

    public static boolean AutoUpdate(Player p, Main plugin){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Essentials e = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

        if(board.getEntryTeam(p.getName()) == null) {
            //first join
            board.getTeam("Unflaired").addEntry(p.getName());
            String toNick = ChatColor.getLastColors(board.getEntryTeam(p.getName()).getPrefix()) + ChatColor.stripColor(p.getName());
            e.getUser(p).setNickname(toNick);
            MessageUnflaired(p);

            return true;
        }

//        File file = new File(plugin.getDataFolder().getParent() + File.separator + "Essentials" + File.separator + "userdata" + File.separator + p.getUniqueId() + ".yml");
//
//        if(!file.exists()){
//            p.sendMessage(ChatColor.DARK_RED + "Essentials has not generated a user data file for you yet, it seems...");
//            return true;
//        }
//
//        YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
//        String nick = yc.getString("nickname");


        //so much better with Essentials imported!
        String nick = e.getUser(p).getNickname();

        if(nick == null){
            nick = p.getName();
        }

        //checks if person's nick should not be changed e.g. an admin with a nickname prefix.
        if(!(p.hasPermission("PolQuadrants.doNotUpdateNick"))){
            String toNick = ChatColor.getLastColors(board.getEntryTeam(p.getName()).getPrefix()) + ChatColor.stripColor(nick);
            e.getUser(p).setNickname(toNick);
            return true;
        }

        if(board.getEntryTeam(p.getName()).getName().equals("Unflaired")){
            String toNick = ChatColor.getLastColors(board.getEntryTeam(p.getName()).getPrefix()) + ChatColor.stripColor(p.getName());
            e.getUser(p).setNickname(toNick);
            MessageUnflaired(p);
            return true;
        }

        return false;
    }

}
