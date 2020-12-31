package com.gmail.nimadastmalchi.teleport;

import com.gmail.nimadastmalchi.teleport.commands.TeleportCommand;
import com.gmail.nimadastmalchi.teleport.listeners.PlayerInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class Main extends JavaPlugin {
    public static HashSet<String> actives;

    @Override
    public void onEnable() {
        actives = new HashSet<>();

        new TeleportCommand(this);
        new PlayerInteractListener(this);
    }
}
