package xyz.iscarter.simplebounty.commands.bounty;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iscarter.simplebounty.SimpleBounty;
import xyz.iscarter.simplebounty.models.Bounty;
import xyz.iscarter.simplebounty.utils.BountiesStorageUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BountySetCommand {
    private static DecimalFormat df = new DecimalFormat("#.##");


    public static boolean bountySet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Error; You must be a player");
                return false;
            }
            if (!sender.hasPermission("simplebounty.command.set")) {
                sender.sendMessage(ChatColor.RED + "Error: No Permission");
                return false;
            }

            if(args.length >= 2) {
                Player p = (Player) sender;


                String targetName = args[0];
                Player target = Bukkit.getPlayer(targetName);


                Double amount = Double.parseDouble(args[1]);

                amount = Double.parseDouble(df.format(amount));

                Double minAmount = SimpleBounty.getPlugin().getConfig().getDouble("min_bounty_amount");

                if(amount < minAmount) {
                    p.sendMessage(ChatColor.RED + "Error: Bounty amount must be over " + ChatColor.YELLOW +  minAmount);
                    return false;
                }

                Boolean targetHasBounty = false;

                Bounty currentBounty = null;

                ArrayList<Bounty> bounties = BountiesStorageUtils.getAllBounties();


                if(payBounty(amount, p)) {


                    for (int i = 0; i < bounties.size(); i++) {
                        if (bounties.get(i).getPlayerName().equals(targetName)) {
                            targetHasBounty = true;
                            currentBounty = bounties.get(i);
                            break;
                        }
                    }


                    if (target == null) {
                        setBounty(null, amount, targetName, targetHasBounty, p, currentBounty);
                        Economy econ = SimpleBounty.getEconomy();
                        p.sendMessage(ChatColor.GREEN + "Your new balance is " + econ.getBalance(p));
                        return true;
                    } else {
                        String targetUUID = target.getUniqueId().toString();
                        setBounty(targetUUID, amount, targetName, targetHasBounty, p, currentBounty);
                        Economy econ = SimpleBounty.getEconomy();
                        p.sendMessage(ChatColor.GREEN + "Your new balance is " + econ.getBalance(p));
                        return true;
                    }

                }

            } else {
                sender.sendMessage(ChatColor.RED + "Error: You must specify a name and an amount");
                sender.sendMessage(ChatColor.RED + "Example: /bounty set Notch 123");
            }

        return false;
    }


    private static boolean payBounty(Double amount, Player p) {
        Economy econ = SimpleBounty.getEconomy();

        if(econ.getBalance(p) >= amount) {
            econ.withdrawPlayer(p, amount);
            return true;
        }
        p.sendMessage(ChatColor.RED + "Error: You do not have enough money");
        return false;
    }


    private static void setBounty(String targetUUID, Double amount, String targetName, Boolean targetHasBounty, Player p, Bounty currentBounty) {


        if (!targetHasBounty) {

            BountiesStorageUtils.createBounty(targetUUID, amount, targetName);

            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.GREEN + "Successfully set bounty of " + ChatColor.YELLOW + "$" + amount + ChatColor.GREEN + " on " + ChatColor.YELLOW + targetName);

            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.GREEN + "A bounty of " + ChatColor.YELLOW + "$" + amount + ChatColor.GREEN + " has been set on " + ChatColor.YELLOW + targetName);
            });

        } else {

            Double previousAmount = currentBounty.getAmount();

            Double newAmount = previousAmount + amount;
            newAmount = Double.parseDouble(df.format(newAmount));

            BountiesStorageUtils.deleteBounty(currentBounty.getPlayerName());
            BountiesStorageUtils.createBounty(targetUUID, newAmount, targetName);

            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.GREEN + "Successfully set bounty of " + ChatColor.YELLOW + "$" + amount + ChatColor.GREEN + " on " + ChatColor.YELLOW + targetName);

            Double finalNewAmount = newAmount;
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.GREEN + "A bounty of " + ChatColor.YELLOW + "$" + amount + ChatColor.GREEN + " has been set on " + ChatColor.YELLOW + targetName);
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.YELLOW + targetName + "'s" + ChatColor.GREEN + " bounties now total " + ChatColor.YELLOW + "$" + finalNewAmount);
            });

        }
    }
}
