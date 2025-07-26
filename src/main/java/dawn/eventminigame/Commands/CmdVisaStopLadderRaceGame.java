package dawn.eventminigame.Commands;

import dawn.eventminigame.ConfigManager;
import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.MiniGames.GameState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CmdVisaStopLadderRaceGame implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("visantaraminigame.admin") || !sender.isOp()) return false;
        CmdVisaStartGame.ladderGame.setGameState(GameState.STOP);
        sender.sendMessage(Eventminigame.ChatColor("&cSemua player telah dinyatakan gugur!!"));
        ConfigManager.sendBroadcastMessage("adminstopgame",(Player) sender);
        return true;
    }
}
