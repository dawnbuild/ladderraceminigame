package dawn.eventminigame.Commands;

import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.MiniGames.LadderGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CmdVisaSetLadderRaceSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp()) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        String path = "LadderRace.Spawn.";
        LadderGame.setSpawn(path,player.getLocation());
        player.sendMessage(Eventminigame.ChatColor("Lokasi telah ditetapkan sebagai spawn dan respawnpoint!!"));
        return true;
    }
}
