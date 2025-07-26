package dawn.eventminigame;

import dawn.eventminigame.Commands.CmdVisaStartGame;
import dawn.eventminigame.Player.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ConfigManager {
    static String parentPath = "Message.";
    static String prefix = Eventminigame.getInstance().getConfig().getString(parentPath + "prefix");

    public static void sendPlayerMessage(String messagePath,Player player){
        String path = parentPath+"player." + messagePath;
        if (!Eventminigame.getInstance().getConfig().getStringList(path).isEmpty()){
            for (String str : Eventminigame.getInstance().getConfig().getStringList(path)){
                player.sendMessage(Eventminigame.ChatColor(formatString(str,player)));
            }
            return;
        }
        String str = Eventminigame.getInstance().getConfig().getString(path);
        player.sendMessage(Eventminigame.ChatColor(formatString(Objects.requireNonNull(str),player)));
    }

    public static void sendBroadcastMessage(String messagePath,Player player){
        String path = parentPath +"broadcast."+ messagePath;
        if (!Eventminigame.getInstance().getConfig().getStringList(path).isEmpty()){
            for (String str : Eventminigame.getInstance().getConfig().getStringList(path)){
                Bukkit.broadcastMessage(Eventminigame.ChatColor(formatString(str, player)));
            }
            return;
        }
        String str = Eventminigame.getInstance().getConfig().getString(path);
        Bukkit.broadcastMessage(Eventminigame.ChatColor(formatString(str,player)));
    }

    public static void sendBroadcastMessage(String messagePath,int countdown){
        String path = parentPath +"broadcast."+ messagePath;
        if (!Eventminigame.getInstance().getConfig().getStringList(path).isEmpty()){
            for (String str : Eventminigame.getInstance().getConfig().getStringList(path)){
                Bukkit.broadcastMessage(Eventminigame.ChatColor(formatString(str, countdown)));
            }
            return;
        }
        String str = Eventminigame.getInstance().getConfig().getString(path);
        Bukkit.broadcastMessage(Eventminigame.ChatColor(formatString(str,countdown)));
    }

    static String formatString(String str, Player player){
        return str.replaceAll("%player%", player.getName())
                .replaceAll("%prefix%",prefix)
                .replaceAll("%timer%", ScoreboardManager.formatTime(CmdVisaStartGame.ladderGame.getCurrentTime()));
    }

    static String formatString(String str, int countdown){
        return str.replaceAll("%countdown%", String.valueOf(countdown))
                .replaceAll("%prefix%",prefix)
                .replaceAll("%timer%", ScoreboardManager.formatTime(CmdVisaStartGame.ladderGame.getCurrentTime()));
    }
}
