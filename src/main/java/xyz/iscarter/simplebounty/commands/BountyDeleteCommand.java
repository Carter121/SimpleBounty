package xyz.iscarter.simplebounty.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iscarter.simplebounty.utils.BountiesStorageUtils;


public class BountyDeleteCommand {

    public static boolean bountyDelete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Error; You must be a player");
            return false;
        }

            if (!sender.hasPermission("simplebounty.command.delete")) {
                sender.sendMessage(ChatColor.RED + "Error: No Permission");
                return false;
            }

            Player p = (Player) sender;

            if (args.length < 1) {
                p.sendMessage(ChatColor.RED + "Error: Must supply player name");
                return false;
            }


            String target = args[0];

            BountiesStorageUtils.deleteBounty(target);

            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.GREEN + "Removed all bounties from " + ChatColor.YELLOW + target);


        return false;
    }
}
