package com.pandapulsestudios.amongusminecraft.Commands;

import com.pandapulsestudios.amongusminecraft.Enums.GameTypes;
import com.pandapulsestudios.amongusminecraft.PulseMatchmaking.Lobbies.TheSkeldNormal;
import com.pandapulsestudios.pulsecommands.Enums.TabType;
import com.pandapulsestudios.pulsecommands.Interface.*;
import com.pandapulsestudios.pulsecommands.PlayerCommand;
import com.pandapulsestudios.pulsematchmaking.API.LobbyAPI;
import org.bukkit.Bukkit;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PCCommand
public class AmongUsCommands extends PlayerCommand {
    public AmongUsCommands() {
        super("AmongUs", false);
    }

    @PCMethod
    @PCSignature("Join")
    @PCTab(pos = 0, type = TabType.Information_From_Function, data = "GameTypeNames")
    public void AmongUsJoin(UUID playerUUID, String gameTypeString){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        var gameType = GameTypes.valueOf(gameTypeString);
        if(gameType == GameTypes.TheSkeldNormal) TheSkeldNormal.JoinRandomRoom(player);
    }

    @PCMethod
    @PCSignature("base")
    public void BaseWorld(UUID playerUUID){
        var player = Bukkit.getPlayer(playerUUID);
        if(player == null) return;
        player.teleport(Bukkit.getWorld("AmongUSMinecraft").getSpawnLocation());
    }

    @PCMethodData
    public List<String> GameTypeNames(String currentData){
        var data = new ArrayList<String>();
        for(var gameType : GameTypes.values()){
            if(gameType.name().contains(currentData)) data.add(gameType.name());
        }
        return data;
    }
}
