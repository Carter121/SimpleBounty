package xyz.iscarter.simplebounty.commands.killStreak;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iscarter.simplebounty.utils.SamePlayerKillStreakUtils;

public class KillStreakCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Error: You must be a player to run this");
        }

        if(!sender.hasPermission("simplebounty.command.")) {
            sender.sendMessage(ChatColor.RED + "Error: You do not have permission");
        }

        Player p = (Player) sender;

        Player target = Bukkit.getPlayer(args[0]);

        int streak = SamePlayerKillStreakUtils.getKill(p.getUniqueId().toString(), target.getUniqueId().toString()).getKillsInTime();

        p.sendMessage(String.valueOf(streak));

        return false;
    }

}
