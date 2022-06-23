package xyz.iscarter.simplebounty.commands;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iscarter.simplebounty.models.Bounty;
import xyz.iscarter.simplebounty.utils.BountiesStorageUtils;

import java.util.ArrayList;
import java.util.UUID;

public class BountyListCommand {

    public static boolean bountyList(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Error; You must be a player");
                return false;
            }

            if(args.length > 1) {

                if(canBeInt(args[1])) {

                    if (!sender.hasPermission("simplebounty.command.list.pages")) {
                        sender.sendMessage(ChatColor.RED + "Error: No Permission");
                        return false;
                    }

                    Player p = (Player) sender;

                    ArrayList<Bounty> bounties = BountiesStorageUtils.getAllBounties();

                    int maxPages = (bounties.size() / 5);
                    maxPages++;


                    int currentPage = Integer.parseInt(args[1]);

                    int howManyPerPage = 5;


                    displayBounties(currentPage * 5 - 4, currentPage * 5, bounties, p, currentPage, maxPages);


                    if(currentPage > maxPages) {
                        p.sendMessage(ChatColor.RED + "Error: That page does not exist");
                        return false;
                    }


                    if(bounties.size() == 0) {

                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.GREEN + "There are no active bounties :)");

                        return true;

                    }

                    return true;

                }

                if(args[1].equals("self")) {

                    if (!sender.hasPermission("simplebounty.command.list.self")) {
                        sender.sendMessage(ChatColor.RED + "Error: No Permission");
                        return false;
                    }

                    Player p = (Player) sender;

                    ArrayList<Bounty> bounties = BountiesStorageUtils.getAllBounties();


                    for (int i = 0; i < bounties.size(); i++) {


                        String player = bounties.get(i).getPlayerName();
                        Double amount  = bounties.get(i).getAmount();

                        if(bounties.get(i).getPlayerName().equals(p.getName())) {
                            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " +  ChatColor.RED + "You have a bounty for " + ChatColor.YELLOW + "$" + amount);
                            return true;
                        }
                    }

                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.GREEN + "No bounties found");
                    return true;

                }
            }

                if (!sender.hasPermission("simplebounty.command.list")) {
                    sender.sendMessage(ChatColor.RED + "Error: No Permission");
                    return false;
                }


                Player p = (Player) sender;

                ArrayList<Bounty> bounties = BountiesStorageUtils.getAllBounties();

                if(bounties.size() == 0) {

                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.GREEN + "There are no active bounties :)");

                    return true;

                }

                for (int i = 0; i < bounties.size(); i++) {

                    String player = bounties.get(i).getPlayerName();
                    Double amount = bounties.get(i).getAmount();

                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.YELLOW + player + ChatColor.RED + " FOR " + ChatColor.YELLOW + "$" + amount);
                }
                return true;

            }


    private static Boolean canBeInt(String number) {

        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private static void displayBounties(int min, int max, ArrayList<Bounty> bounties, Player p, int currentPage, int maxPages) {

        min--;
        max--;

        p.sendMessage(ChatColor.YELLOW + "----------------");

        for (int i = min; i < bounties.size(); i++) {

            String player = bounties.get(i).getPlayerName();
            Double amount = bounties.get(i).getAmount();

            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOUNTY] " + ChatColor.YELLOW + player + ChatColor.RED + " FOR " + ChatColor.YELLOW + "$" + amount);

            if(i == max) break;
        }

        p.sendMessage(ChatColor.YELLOW + "Page " + currentPage + "/" + maxPages);
        p.sendMessage(ChatColor.YELLOW + "Do /bounty list <page number>");
        p.sendMessage(ChatColor.YELLOW + "----------------");

    }

    }
