package me.Snaxolas.PolQuadrants.Commands;

import me.Snaxolas.PolQuadrants.AutoUpdate;
import me.Snaxolas.PolQuadrants.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class SetQuadrant implements CommandExecutor {

    static Main plugin;

    public SetQuadrant(Main main){
        plugin = main;
    }



    public String teamStringSanitize(String s){

        if(s.equalsIgnoreCase("LibLeft")){
            return "LibLeft";
        }else if(s.equalsIgnoreCase("LibCenter")){
            return "LibCenter";
        }else if(s.equalsIgnoreCase("LibRight")){
            return "LibRight";
        }else if(s.equalsIgnoreCase("RightCenter")){
            return "RightCenter";
        }else if(s.equalsIgnoreCase("AuthRight")){
            return "AuthRight";
        }else if(s.equalsIgnoreCase("AuthCenter")){
            return "AuthCenter";
        }else if(s.equalsIgnoreCase("AuthLeft")){
            return "AuthLeft";
        }else if(s.equalsIgnoreCase("LeftCenter")){
            return "LeftCenter";
        }else if(s.equalsIgnoreCase("Centrist")){
            return "Centrist";
        }else if(s.equalsIgnoreCase("OffCompass")){
            return "OffCompass";
        }

        return "Failed";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        if(label.equalsIgnoreCase("setquadrant") || label.equalsIgnoreCase("setq")){
            if(!(sender.hasPermission("PolQuadrants.changeFlair"))){
                //failed -- no perms
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to do that!");
                return true;
            }

            if(args.length == 0){
                //success -- delivered usage instructions
                sender.sendMessage("Usage: /setquadrant <Quadrant/Center>");
                if(sender.hasPermission("PolQuadrants.changeFlair.others")){
                    sender.sendMessage("OR /setquadrant <Username> <Quadrant/Center>");
                }
                return true;
            }


            if(Bukkit.getPlayer(args[0]) != null){
                //changing another's flair

                //player to have their flair changed.
                Player p = Bukkit.getPlayer(args[0]);

                if(!(sender.hasPermission("PolQuadrants.changeFlair.others"))){

                    //failed -- no perms
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to change others' flairs!");
                    return true;
                }
                if(args.length > 1){

                    if(teamStringSanitize(args[1]) == "Failed"){
                        //failed -- Invalid second argument
                        sender.sendMessage(ChatColor.DARK_RED + "Invalid Quadrant/Center.");
                        return true;
                    }

                    //success -- changed others' flair
                    Team t = board.getTeam(teamStringSanitize(args[1]));
                    t.addEntry(p.getName());
                    sender.sendMessage(ChatColor.DARK_GREEN + "Successfully changed " + Bukkit.getPlayer(args[0]).getName() + "'s flair");
                    p.sendMessage(ChatColor.DARK_GREEN + "Your flair has been changed to " + t.getName());
                    AutoUpdate.AutoUpdate(p, plugin);
                    Bukkit.broadcastMessage(ChatColor.GOLD + p.getName() + " is now " + ChatColor.getLastColors(board.getEntryTeam(p.getName()).getPrefix()) + board.getEntryTeam(p.getName()).getName() + ChatColor.GOLD + ".");
                    return true;
                }
                //failed -- did not give quadrant after player's name
                sender.sendMessage(ChatColor.DARK_RED + "Quadrant/Center required.");
                return true;

            }
            if (sender instanceof Player) {
                //changing own flair
                Player p = (Player) sender;
                if(teamStringSanitize(args[0]) == "Failed"){
                    //failed -- Invalid second argument
                    sender.sendMessage(ChatColor.DARK_RED + "Invalid Quadrant/Center.");
                    return true;
                }
                //success -- changed own flair
                Team t = board.getTeam(teamStringSanitize(args[0]));
                t.addEntry(p.getName());
                p.sendMessage(ChatColor.DARK_GREEN + "Your flair has been changed to " + t.getName() + ".");
                AutoUpdate.AutoUpdate(p, plugin);
                Bukkit.broadcastMessage(ChatColor.GOLD + p.getName() + " is now " + ChatColor.getLastColors(board.getEntryTeam(p.getName()).getPrefix()) + board.getEntryTeam(p.getName()).getName() + ChatColor.GOLD + ".");
                return true;
            }
            //failed -- sender is console (or anything but a player) and did not have a valid first argument.
            sender.sendMessage(ChatColor.DARK_RED + "Invalid args.");
            return onCommand(sender ,command ,label ,new String[0]);

        }


        return false;
    }
}
