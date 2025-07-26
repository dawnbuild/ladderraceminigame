package dawn.eventminigame.Commands;

import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.MiniGames.CustomItem.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CmdVisaGiveKBStick implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp()) return false;
        if (args.length==1){
            if (args[0].equalsIgnoreCase("*")){
                for (Player player : Bukkit.getOnlinePlayers()){
                    player.getInventory().addItem(CustomItemManager.KnockbackStick);
                }
                sender.sendMessage(Eventminigame.ChatColor("&eMemberikan Knockback Stick"));
            }
            else{
                if (!Bukkit.getPlayer(args[0]).isOnline()){
                    sender.sendMessage(Eventminigame.ChatColor("&ePlayer dengan nama &b"+ args[0] +" &cTIDAK &edapat di temukan"));
                    return false;
                }
                Bukkit.getPlayer(args[0]).getInventory().addItem(CustomItemManager.KnockbackStick);
                sender.sendMessage(Eventminigame.ChatColor("&eMemberikan Knockback Stick kepada &b" + args[0]));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tabCompleter = new ArrayList<>();
        tabCompleter.add("*");
        for (Player players : Bukkit.getOnlinePlayers()){
            String playyerName = players.getName();
            tabCompleter.add(playyerName);
        }
        return tabCompleter;
    }
}
