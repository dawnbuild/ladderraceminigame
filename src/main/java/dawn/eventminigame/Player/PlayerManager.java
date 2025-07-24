package dawn.eventminigame.Player;

import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.MiniGames.LadderRace.LadderGame;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    public static Map<Player,PlayerState> playerManager = new HashMap<>();
    public static List<String> winners = new ArrayList<>();
    public static void setPlayerState(Player player, PlayerState playerState){
        playerManager.remove(player);
        switch (playerState){
            case PLAYING:
                player.sendMessage(Eventminigame.ChatColor("&eYou are selected as a participant of this game!"));
                player.sendMessage(Eventminigame.ChatColor("&eTry your best &b" + player.getName() + "&r!!"));
                playerManager.put(player,PlayerState.PLAYING);
                Eventminigame.changePlayerGamemode(player,GameMode.SURVIVAL);
                break;
            case WIN:
                ScoreboardManager.restorePlayerScoreboard(player);
                player.sendTitle(Eventminigame.ChatColorNonPrefix("&eYOU WIN!!!"),"");
                Eventminigame.playSoundToAllPlayer(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST);
                player.sendMessage(Eventminigame.ChatColor("YOU'V SUCCESSFULLY REACH THE FINISH!!!"));
                playerManager.put(player,PlayerState.WIN);
                LadderGame.totalWinner+=1;
                winners.add(player.getName());
                Eventminigame.changePlayerGamemode(player, GameMode.SPECTATOR);
                break;
            case LOSER:
                ScoreboardManager.restorePlayerScoreboard(player);
                player.damage(999);
                player.sendTitle(Eventminigame.ChatColorNonPrefix("&cYOU LOSE!!!"),"");
                player.sendMessage(Eventminigame.ChatColor("&eYou Lost At This Game &c:("));
                player.sendMessage(Eventminigame.ChatColor("&eTry your best, maybe next time &b;)"));
                break;
        }
    }

    public static boolean isPlayerPlaying(Player player){
        return playerManager.containsKey(player) && playerManager.get(player).equals(PlayerState.PLAYING);
    }
    public static void saveWinnerData(){
        Eventminigame.getInstance().getConfig().set("Winner",winners);
        Eventminigame.getInstance().saveConfig();
        Eventminigame.getInstance().reloadConfig();
    }
}
