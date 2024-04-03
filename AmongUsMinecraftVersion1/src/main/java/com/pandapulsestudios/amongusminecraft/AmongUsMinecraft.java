package com.pandapulsestudios.amongusminecraft;

import com.pandapulsestudios.pulsecommands.PulseCommands;
import com.pandapulsestudios.pulsecore.Java.ClassAPI;
import com.pandapulsestudios.pulsematchmaking.API.LobbyAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class AmongUsMinecraft extends JavaPlugin {
    @Override
    public void onEnable() {
        ClassAPI.RegisterClasses(this);
        PulseCommands.RegisterRaw(this);
        LobbyAPI.RegisterCustomLobbies(this);
    }
}
