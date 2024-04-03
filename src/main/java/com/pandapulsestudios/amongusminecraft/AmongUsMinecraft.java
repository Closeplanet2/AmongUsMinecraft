package com.pandapulsestudios.amongusminecraft;

import com.pandapulsestudios.pulsecommands.PulseCommands;
import com.pandapulsestudios.pulsecore.Java.ClassAPI;
import com.pandapulsestudios.pulsecore.Player.VanishAPI;
import com.pandapulsestudios.pulsematchmaking.API.LobbyAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AmongUsMinecraft extends JavaPlugin {
    @Override
    public void onEnable() {
        ClassAPI.RegisterClasses(this);
        PulseCommands.RegisterRaw(this);
        LobbyAPI.RegisterCustomLobbies(this);
        for(var player : Bukkit.getOnlinePlayers()){
            for(var viewer : Bukkit.getOnlinePlayers()){
                VanishAPI.ShowTargetToViewer(viewer, player);
                VanishAPI.ShowTargetToViewer(player, viewer);
            }
        }
    }
}
