package com.pandapulsestudios.amongusminecraft.Objects;

import com.pandapulsestudios.amongusminecraft.Enums.WireColor;
import com.pandapulsestudios.amongusminecraft.Enums.WirePortType;
import com.pandapulsestudios.amongusminecraft.Interface.GameTask;
import com.pandapulsestudios.pulsecore.Data.API.UUIDDataAPI;
import com.pandapulsestudios.pulsecore.Player.Enums.PlayerAction;
import com.pandapulsestudios.pulsecore.Player.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class WireTask implements GameTask {

    private Location taskLocation;
    private HashMap<Location, WireTaskPort> wireTaskPorts = new HashMap<>();
    private Player playerInTask;
    private Location previousLocation;

    @Override
    public GameTask SetupTask(Location taskLocation) {
        this.taskLocation = taskLocation;

        var colorList = new ArrayList<WireColor>();
        var vectorIndex = 0;
        while(colorList.size() < WireColor.values().length && vectorIndex < 4){
            var randomIndex = new Random().nextInt(WireColor.values().length);
            var randomColor = WireColor.values()[randomIndex];
            if(colorList.contains(randomColor)) continue;
            var nextVectorOffset = new Vector(4.0, 3.0, 8.0).subtract(new Vector(0, vectorIndex * 2, 0));
            var nextLocation = taskLocation.clone().add(nextVectorOffset);
            wireTaskPorts.put(nextLocation, new WireTaskPort(nextLocation, randomColor, WirePortType.INPUT));
            colorList.add(randomColor);
            vectorIndex += 1;
        }

        colorList = new ArrayList<WireColor>();
        vectorIndex = 0;
        while(colorList.size() < WireColor.values().length && vectorIndex < 4){
            var randomIndex = new Random().nextInt(WireColor.values().length);
            var randomColor = WireColor.values()[randomIndex];
            if(colorList.contains(randomColor)) continue;
            var nextVectorOffset = new Vector(-4.0, 3.0, 8.0).subtract(new Vector(0, vectorIndex * 2, 0));
            var nextLocation = taskLocation.clone().add(nextVectorOffset);
            wireTaskPorts.put(nextLocation, new WireTaskPort(nextLocation, randomColor, WirePortType.OUTPUT));
            colorList.add(randomColor);
            vectorIndex += 1;
        }

        return this;
    }

    @Override
    public void StartGameTask(Player playerInTask) {
        this.playerInTask = playerInTask;
        this.previousLocation = this.playerInTask.getLocation();
        this.playerInTask.teleport(taskLocation);
        UUIDDataAPI.STORE(this.playerInTask.getUniqueId(), "StoredLocation", taskLocation);
        PlayerAPI.TogglePlayerAction(PlayerAction.PlayerMove, false, this.playerInTask);
    }

    @Override
    public boolean IsPlayerInTask(Player playerInTask) {
        return this.playerInTask == playerInTask;
    }

    @Override
    public void EndGameTask(Player playerInTask) {
        if(this.playerInTask != playerInTask) return;
        this.playerInTask.teleport(this.previousLocation);
        PlayerAPI.TogglePlayerAction(PlayerAction.PlayerMove, true, this.playerInTask);
        this.playerInTask = null;
        this.previousLocation = null;
    }
}
