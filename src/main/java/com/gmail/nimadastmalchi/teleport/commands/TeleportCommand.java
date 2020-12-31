package com.gmail.nimadastmalchi.teleport.commands;

import com.gmail.nimadastmalchi.teleport.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    private Main plugin;

    public TeleportCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("teleport").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may execute this command.");
            return true;
        }

        Player p = (Player) sender;
        if (args.length != 0) {
            p.sendMessage(ChatColor.RED + "Incorrect number of arguments for this command. Try again.");
            return true;
        }

        if (Main.actives.contains(p.getName())) {
            p.sendMessage(ChatColor.WHITE + "Teleport:" + ChatColor.RED + " [ON]" + ChatColor.GREEN + " [OFF]");
            Main.actives.remove(p.getName());
        } else {
            p.sendMessage(ChatColor.WHITE + "Teleport:" + ChatColor.GREEN + " [ON]" + ChatColor.RED + " [OFF]");
            Main.actives.add(p.getName());
        }
        return true;
    }
}
