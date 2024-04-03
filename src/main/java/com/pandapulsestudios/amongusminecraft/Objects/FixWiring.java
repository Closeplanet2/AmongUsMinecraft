package com.pandapulsestudios.amongusminecraft.Objects;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.amongusminecraft.Enums.TaskStartType;
import com.pandapulsestudios.amongusminecraft.Enums.WireColor;
import com.pandapulsestudios.amongusminecraft.Enums.WirePortType;
import com.pandapulsestudios.amongusminecraft.Interface.GameTask;
import com.pandapulsestudios.amongusminecraft.Libraries.LineDrawing;
import com.pandapulsestudios.amongusminecraft.Objects.SubTask.IndividualWireTask;
import com.pandapulsestudios.amongusminecraft.Objects.SubTask.WireTaskArea;
import com.pandapulsestudios.pulsecore.Data.API.UUIDDataAPI;
import com.pandapulsestudios.pulsecore.Player.Enums.PlayerAction;
import com.pandapulsestudios.pulsecore.Player.PlayerAPI;
import com.pandapulsestudios.pulsematchmaking.Objects.NetworkRoom;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

public class FixWiring implements GameTask {

    private final Integer numberOfWireTasks = 3;
    private LinkedHashMap<GameTaskArea, WireTaskArea> WireTaskAreas = new LinkedHashMap<>();
    private LinkedHashMap<UUID, LinkedHashMap<GameTaskArea, Boolean>> GameTaskAreaPerPlayer = new LinkedHashMap<>();
    private HashMap<UUID, GameTaskArea> PlayerPerArea = new HashMap<>();

    @Override
    public List<GameTaskArea> MasterAreaList() {
        return Arrays.asList(new GameTaskArea[]{GameTaskArea.Electrical, GameTaskArea.Storage, GameTaskArea.Admin,
                GameTaskArea.UpperNavigation, GameTaskArea.Cafeteria,GameTaskArea.Security});
    }

    @Override
    public void SetupGameTask(NetworkRoom networkRoom) {
        for(var i  = 0 ; i < MasterAreaList().size(); i++){
            var gameTaskArea = MasterAreaList().get(i);
            var taskLocation = new Location(networkRoom.GameWorld().ReturnGameWorld(), 9.5, 8, 6 - (i * 14));
            WireTaskAreas.put(gameTaskArea, new WireTaskArea(networkRoom, taskLocation));
        }
    }

    @Override
    public void SetupGameTaskForPlayer(NetworkRoom networkRoom, Player player) {
        var randomTaskList = new LinkedHashMap<GameTaskArea, Boolean>();
        var randomStartIndex = new Random().nextInt(MasterAreaList().size());
        for(var i = 0; i < numberOfWireTasks; i++){
            var taskIndex = i + randomStartIndex;
            if(taskIndex >= MasterAreaList().size()) taskIndex -= MasterAreaList().size();
            player.sendMessage(MasterAreaList().get(taskIndex).areaName);
            randomTaskList.put(MasterAreaList().get(taskIndex), false);
        }
        GameTaskAreaPerPlayer.put(player.getUniqueId(), randomTaskList);
    }

    @Override
    public boolean IsDoingTask(Player player) {
        return PlayerPerArea.containsKey(player.getUniqueId());
    }

    @Override
    public boolean TryAndStartGameTask(NetworkRoom networkRoom, Player player, TaskStartType taskStartType, Location triggerLocation) {
        player.sendMessage(String.format("Trying to start (%s) and PlayerPerArea Cotnains (%s)", taskStartType.toString(), PlayerPerArea.containsKey(player.getUniqueId())));
        if(taskStartType == TaskStartType.CLICK_TO_START && !IsDoingTask(player)){
            var currentGameTaskArea = ReturnNextGameTaskArea(player);
            if(currentGameTaskArea == null) return false;
            if(currentGameTaskArea == GameTaskArea.Electrical && triggerLocation.toVector().equals(new Vector(25.0, 20.0, -13.0))){
                WireTaskAreas.get(currentGameTaskArea).StartTaskForPlayer(networkRoom, player);
                PlayerPerArea.put(player.getUniqueId(), currentGameTaskArea);
            }else if(currentGameTaskArea == GameTaskArea.Storage && triggerLocation.toVector().equals(new Vector(11.0, 20.0, -17.0))){
                WireTaskAreas.get(currentGameTaskArea).StartTaskForPlayer(networkRoom, player);
                PlayerPerArea.put(player.getUniqueId(), currentGameTaskArea);
            }else if(currentGameTaskArea == GameTaskArea.Admin && triggerLocation.toVector().equals(new Vector(5.0, 20.0, -9.0))){
                WireTaskAreas.get(currentGameTaskArea).StartTaskForPlayer(networkRoom, player);
                PlayerPerArea.put(player.getUniqueId(), currentGameTaskArea);
            }else if(currentGameTaskArea == GameTaskArea.UpperNavigation && triggerLocation.toVector().equals(new Vector(-29.0, 20.0, -2.0))){
                WireTaskAreas.get(currentGameTaskArea).StartTaskForPlayer(networkRoom, player);
                PlayerPerArea.put(player.getUniqueId(), currentGameTaskArea);
            }else if(currentGameTaskArea == GameTaskArea.Cafeteria && triggerLocation.toVector().equals(new Vector(19.0, 20.0, 13.0))){
                WireTaskAreas.get(currentGameTaskArea).StartTaskForPlayer(networkRoom, player);
                PlayerPerArea.put(player.getUniqueId(), currentGameTaskArea);
            }else if(currentGameTaskArea == GameTaskArea.Security && triggerLocation.toVector().equals(new Vector(40, 20.0, -7.0))){
                WireTaskAreas.get(currentGameTaskArea).StartTaskForPlayer(networkRoom, player);
                PlayerPerArea.put(player.getUniqueId(), currentGameTaskArea);
            }
        }
        return false;
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event, NetworkRoom networkRoom, Player player) {
        var currentGameTaskArea = ReturnNextGameTaskArea(player);
        if(currentGameTaskArea == null) return true;
        var completedTask = WireTaskAreas.get(currentGameTaskArea).PlayerInteractEvent(event, networkRoom, player);
        if(completedTask) EndGameTask(networkRoom, player ,false);
        return true;
    }

    @Override
    public void GameLoop(NetworkRoom networkRoom, int timeElapsed, int timeToReach, boolean reachedEnd) {
        for(var player : networkRoom.ReturnAllPlayers()){
            var currentGameTaskArea = ReturnNextGameTaskArea(player);
            if(currentGameTaskArea == null) return;
            WireTaskAreas.get(currentGameTaskArea).GameLoop(networkRoom, player, timeElapsed, timeToReach, reachedEnd);
        }
    }

    @Override
    public void EndGameTask(NetworkRoom networkRoom, Player player, boolean cancelledTask) {
        var currentGameTaskArea = ReturnNextGameTaskArea(player);
        if(currentGameTaskArea == null) return;
        WireTaskAreas.get(currentGameTaskArea).EndTaskForPlayer(networkRoom, player);
        PlayerPerArea.remove(player.getUniqueId());
        if(cancelledTask) return;
        var userData = GameTaskAreaPerPlayer.get(player.getUniqueId());
        userData.put(currentGameTaskArea, true);
        GameTaskAreaPerPlayer.put(player.getUniqueId(), userData);
    }

    private GameTaskArea ReturnNextGameTaskArea(Player player){
        var gameTaskAreas = GameTaskAreaPerPlayer.getOrDefault(player.getUniqueId(), new LinkedHashMap<>());
        for(var gameTaskArea : gameTaskAreas.keySet()){
            if(!gameTaskAreas.get(gameTaskArea)) return gameTaskArea;
        }
        return null;
    }

}
