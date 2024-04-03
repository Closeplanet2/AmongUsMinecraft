package com.pandapulsestudios.amongusminecraft.Commands;

import com.pandapulsestudios.amongusminecraft.PulseMatchmaking.TheSkeldNormal;
import com.pandapulsestudios.pulsecommands.Enums.TabType;
import com.pandapulsestudios.pulsecommands.Interface.*;
import com.pandapulsestudios.pulsecommands.PlayerCommand;
import org.bukkit.Bukkit;

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
        if(gameTypeString.equals("TheSkeldNormal")) TheSkeldNormal.JoinRandomRoom(player);
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
        if("TheSkeldNormal".contains(currentData)) data.add("TheSkeldNormal");
        return data;
    }
}
