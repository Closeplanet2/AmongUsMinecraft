package com.pandapulsestudios.amongusminecraft.Objects.SubTask;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.amongusminecraft.Enums.WireColor;
import com.pandapulsestudios.pulsematchmaking.Objects.NetworkRoom;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WireTaskArea {
    public List<IndividualWireTask> IndividualWireTasks = new ArrayList<>();

    public WireTaskArea(NetworkRoom networkRoom, Location taskLocation){
        var inputColors = GenerateRandomColorList();
        var outputColors = GenerateRandomColorList();
        for(var i = 0; i < networkRoom.NumberOfPlayersInRoom(); i++){
            var newTaskLocation = taskLocation.clone().subtract(new Vector(i * 13, 0, 0));
            IndividualWireTasks.add(new IndividualWireTask(newTaskLocation, inputColors, outputColors));
        }
    }

    private List<WireColor> GenerateRandomColorList(){
        var data = new ArrayList<WireColor>();
        while(data.size() < WireColor.values().length){
            var randomColor = WireColor.values()[new Random().nextInt(WireColor.values().length)];
            if(data.contains(randomColor)) continue;
            data.add(randomColor);
        }
        return data;
    }

    public void StartTaskForPlayer(NetworkRoom networkRoom, Player player){
        var playerIndex = networkRoom.ReturnAllPlayers().indexOf(player);
        var individualWireTask = IndividualWireTasks.get(playerIndex);
        individualWireTask.StartTaskForPlayer(networkRoom, player);
    }

    public void EndTaskForPlayer(NetworkRoom networkRoom, Player player){
        var playerIndex = networkRoom.ReturnAllPlayers().indexOf(player);
        var individualWireTask = IndividualWireTasks.get(playerIndex);
        individualWireTask.EndTaskForPlayer(networkRoom, player);
    }

    public boolean PlayerInteractEvent(PlayerInteractEvent event, NetworkRoom networkRoom, Player player){
        var playerIndex = networkRoom.ReturnAllPlayers().indexOf(player);
        var individualWireTask = IndividualWireTasks.get(playerIndex);
        return individualWireTask.PlayerInteractEvent(event, networkRoom, player);
    }

    public void GameLoop(NetworkRoom networkRoom, Player player, int timeElapsed, int timeToReach, boolean reachedEnd) {
        var playerIndex = networkRoom.ReturnAllPlayers().indexOf(player);
        var individualWireTask = IndividualWireTasks.get(playerIndex);
        individualWireTask.GameLoop(networkRoom, player, timeElapsed, timeToReach, reachedEnd);
    }
}
