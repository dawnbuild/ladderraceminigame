package dawn.eventminigame.Commands;

import dawn.eventminigame.Area;
import dawn.eventminigame.ConfigManager;
import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.MiniGames.GameState;
import dawn.eventminigame.MiniGames.LadderGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CmdVisaStartGame implements CommandExecutor{
    public static LadderGame ladderGame;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("visantaraminigame.admin") || !sender.isOp()) return false;
        if (LadderGame.stTask!=null)ladderGame.setGameState(GameState.STOP);
        Area GameArea = Area.getInstanceFromConfig("LadderRace.GameArea");
        Area FinishArea = Area.getInstanceFromConfig("LadderRace.FinishArea");
        Area gate = Area.getInstanceFromConfig("LadderRace.gate");
        Location spawnLoc = LadderGame.getSpawnLocationFromConfig();
        ladderGame = new LadderGame(GameArea,FinishArea, gate,spawnLoc,spawnLoc.getWorld());
        ladderGame.setGameState(GameState.START);
        ConfigManager.sendBroadcastMessage("adminstartgame", (Player) sender);
        return true;
    }
}
