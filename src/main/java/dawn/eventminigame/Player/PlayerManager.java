package dawn.eventminigame.Player;

import dawn.eventminigame.Eventminigame;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    public static Map<Player,PlayerState> playerManager = new HashMap<>();

    public static void setPlayerState(Player player, PlayerState playerState){
        switch (playerState){
            case PLAYING:
                if (playerManager.containsKey(player)) return;
                player.sendMessage(Eventminigame.ChatColor("&eYou are selected as a participant as this game!"));
                player.sendMessage(Eventminigame.ChatColor("&eTry your best &b" + player.getName() + "&r!!"));
                playerManager.put(player,PlayerState.PLAYING);
            case LOSER:
                player.sendMessage(Eventminigame.ChatColor("&eYou Lost At This Game &c:("));
                player.sendMessage(Eventminigame.ChatColor("&eTry your best, maybe next time &b;)"));
                playerManager.remove(player);
        }
    }

    public static boolean isPlayerPlaying(Player player){
        return playerManager.containsKey(player) || playerManager.get(player).equals(PlayerState.PLAYING);
    }
}
