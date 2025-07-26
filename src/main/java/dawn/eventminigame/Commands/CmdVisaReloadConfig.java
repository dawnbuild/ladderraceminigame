package dawn.eventminigame.Commands;

import dawn.eventminigame.Eventminigame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CmdVisaReloadConfig implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.isOp()) return false;
        Eventminigame.getInstance().reloadConfig();
        commandSender.sendMessage(Eventminigame.ChatColor("&aPlugin Berhasil direload"));
        return true;
    }
}
