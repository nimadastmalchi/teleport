package com.gmail.nimadastmalchi.teleport.listeners;

import com.gmail.nimadastmalchi.teleport.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

public class PlayerInteractListener implements Listener {
    private Main plugin;
    private final static int REACH = 256;
    private final static Material ITEM = Material.BLAZE_ROD;

    public PlayerInteractListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        // Check if player is in actives, player clicked air, player is holding an item, and item is ITEM:
        if (Main.actives.contains(p.getName()) && e.getClickedBlock() == null && e.getItem() != null && e.getItem().getType() == ITEM) {
            float yaw = p.getLocation().getYaw();
            float pitch = p.getLocation().getPitch();

            // Calculate the direction vector:
            float scale = (float) abs(cos(pitch * PI / 180));
            float xDir = (float) -sin(yaw * PI / 180) * scale;
            float yDir = (float) -sin(pitch * PI / 180);
            float zDir = (float) cos(yaw * PI / 180) * scale;

            // The height of the player:
            float height = p.isSneaking() ? (float) 1.5 : (float) 1.8;

            // Print the particles:
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 25, 25), 1);
            for (int i = 1; i <= 50; i++) {
                Location l = p.getLocation().clone().add(i * xDir, i * yDir + height, i * zDir);
                p.getWorld().spawnParticle(Particle.REDSTONE, l, 1, dustOptions);
                if (!l.getBlock().isEmpty() && !l.getBlock().isPassable()) {
                    break;
                }
            }

            // Force a delay:
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            // Teleport the player:
            int i;
            for (i = 5; i <= REACH; i++) {
                Location l = p.getLocation().clone().add(i * xDir, i * yDir + height, i * zDir);
                // Check if the player has hit a non-passable block (such as air and water):
                if (!l.getBlock().isPassable()) {
                    l.subtract(xDir, yDir, zDir);
                    // Change location so that the player is not teleported inside a non-passable block:
                    while (!l.getBlock().isPassable() || !l.clone().add(0,1,0).getBlock().isPassable()) {
                        l.add(0,1,0);
                    }
                    p.teleport(l);
                    p.sendMessage(ChatColor.GREEN + "Teleported.");
                    break;
                }
            }
            if (i > REACH) {
                p.sendMessage(ChatColor.RED + "Distance is too far. Failed to teleport.");
            }
        }
    }
}
