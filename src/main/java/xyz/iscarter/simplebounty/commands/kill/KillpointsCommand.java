package xyz.iscarter.simplebounty.commands.kill;

import it.unimi.dsi.fastutil.ints.IntImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iscarter.simplebounty.models.Kill;
import xyz.iscarter.simplebounty.utils.KillsStorageUtils;

import java.util.UUID;

public class KillpointsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("simplebounty.command.killpoints")) {
                sender.sendMessage(ChatColor.RED + "Error: No Permission");
                return false;
            }

            Player p = (Player) sender;

            if(args.length == 0) {
                p.sendMessage(ChatColor.RED + "----------------");

                p.sendMessage(ChatColor.YELLOW + "/killpoint <player> - Gets the kill points for that player");

                p.sendMessage(ChatColor.RED + "----------------");

                return true;

            } else if(isPlayer(args[0])) {

                Player target = Bukkit.getPlayer(args[0]);
                Kill kills = KillsStorageUtils.getKills(target.getUniqueId().toString());

                int targetKills = 0;

                try {
                    targetKills = kills.getKills();
                } catch (Exception e) {
                    targetKills = 0;
                }

                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[KILL POINTS] " + ChatColor.YELLOW + args[0] + ChatColor.RED + " has " + ChatColor.YELLOW + targetKills + ChatColor.RED + " kill points");

                return true;

            } else if (args[0].equals("set") && isPlayer(args[1]) && isInt(args[2])) {

                if(!sender.hasPermission("simplebounty.command.killpoint.set")) {
                    sender.sendMessage(ChatColor.RED + "Error: No Permission");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[1]);

                String targetUUID = target.getUniqueId().toString();

                KillsStorageUtils.deleteKills(targetUUID);

                int newKills = Integer.parseInt(args[2]);

                KillsStorageUtils.setKill(targetUUID, newKills);

                return true;

            } else {

                p.sendMessage(ChatColor.RED + "Error: The command is wrong or that player does not exist");
                return false;

            }

        } else {

            sender.sendMessage("Error: You must be a player to use this command");
            return false;

        }
    }

    private boolean isPlayer(String name) {

        Player p = Bukkit.getPlayer(name);

        if(p == null) {
            return false;
        }

        return true;

    }

    private boolean isInt(String num) {

        try {

            Integer.parseInt(num);
            return true;

        } catch (NumberFormatException e) {

            return false;

        }

    }
}
