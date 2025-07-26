package dawn.eventminigame.Player;

import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.MiniGames.LadderGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardManager {
    static String path = "Scoreboard.";
    static String tittle = Eventminigame.getInstance().getConfig().getString(path + "title");
    static List<String> scoreboardLines = Eventminigame.getInstance().getConfig().getStringList(path + "scoreboard");

    static Map<Player, Scoreboard> playerOriScoreboard = new HashMap<>();
    public static Map<Player, Scoreboard> sb = new HashMap<>();

    public static void update(int timer) {
        for (Player p : PlayerManager.playerManager.keySet()) {
            // Skip if player doesn't have scoreboard set up
            if (PlayerManager.isPlayerPlaying(p)){
                if (!sb.containsKey(p)) continue;

                Scoreboard board = sb.get(p);
                Objective obj = board.getObjective("ladderrace");
                if (obj == null) continue;

                // Clear previous entries
                for (String entry : board.getEntries()) {
                    board.resetScores(entry);
                }

                // Add new scores
                for (int i = 0; i < scoreboardLines.size(); i++) {
                    String line = scoreboardLines.get(i)
                            .replace("%timer%", formatTime(timer))
                            .replace("%winner%", String.valueOf(LadderGame.totalWinner));
                    obj.getScore(Eventminigame.ChatColor(line)).setScore(scoreboardLines.size() - i);
                }

                p.setScoreboard(board);
            }
        }
    }

    public static void addPlayerScoreboard() {
        for (Player p : PlayerManager.playerManager.keySet()) {
            playerOriScoreboard.put(p, p.getScoreboard());

            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = board.registerNewObjective("ladderrace", "dummy", Eventminigame.ChatColor(tittle));
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            sb.put(p, board);
            p.setScoreboard(board);
        }
    }

    public static void restorePlayerScoreboard(Player p) {
        if (playerOriScoreboard.containsKey(p)) {
            p.setScoreboard(playerOriScoreboard.get(p));
            playerOriScoreboard.remove(p);
        } else {
            p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

    public static String formatTime(int i) {
        int minutes = i / 60;
        int seconds = i % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
