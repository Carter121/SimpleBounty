package xyz.iscarter.simplebounty.commands.bounty;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iscarter.simplebounty.SimpleBounty;

public class BountyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equals("bounty")) {
            if (sender instanceof Player) {
                if (!sender.hasPermission("simplebounty.command.bounty")) {
                    sender.sendMessage(ChatColor.RED + "Error: No Permission");
                    return false;
                }

                Player p = (Player) sender;

                if(args.length == 0) {
                    p.sendMessage(ChatColor.YELLOW + "----------------");
                    p.sendMessage(ChatColor.RED + "/bounty list " + ChatColor.YELLOW + "- Lists all active bounties");
                    p.sendMessage(ChatColor.RED + "/bounty list self " + ChatColor.YELLOW + "- Displays the bounty on you (if there is one)");
                    p.sendMessage(ChatColor.YELLOW + "----------------");

                    return true;
                }

                if (args.length == 1) {

                    if (args[0].equals("list")) {

                        String[] listArgs = {"list"};
                        BountyListCommand.bountyList(sender, command, listArgs);
                        return true;

                    }

                    p.sendMessage(ChatColor.RED + "Error: Command is wrong");
                    return false;

                } else {
                    if (args[0].equals("list") && args[1].equals("self")) {

                        String[] listArgs = {"list", "self"};
                        BountyListCommand.bountyList(sender, command, listArgs);
                        return true;

                    } else if(args[0].equals("list") && canBeInt(args[1])) {
                        
                        String[] listArgs = {"list", args[1]};
                        BountyListCommand.bountyList(sender, command, listArgs);
                        return true;

                    } else if (args[0].equals("delete")) {

                        String[] listArgs = {args[1]};
                        BountyDeleteCommand.bountyDelete(sender, command, listArgs);
                        return true;

                    }

                    p.sendMessage(ChatColor.RED + "Error: Command is wrong");
                    return false;

                }

            }

        } else {
            sender.sendMessage(ChatColor.RED + "Error: You must be a player to run this command");
            return false;
        }

        return false;
    }

    private Boolean canBeDouble(String number) {

        try {
            Double.parseDouble(number);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private Boolean canBeInt(String number) {

        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private void setCommandWrong(Player p) {
        p.sendMessage(ChatColor.RED + "Error: Command is wrong");
        p.sendMessage(ChatColor.RED + "Error: You must specify a name and an amount");
        p.sendMessage(ChatColor.RED + "Example: /bounty set Notch 123");
    }
}
