package com.pandapulsestudios.amongusminecraft.Libraries;

import com.google.common.graph.Network;
import com.pandapulsestudios.amongusminecraft.Enums.GameMap;
import com.pandapulsestudios.amongusminecraft.Enums.StoredTask;
import com.pandapulsestudios.amongusminecraft.Enums.TaskLength;
import com.pandapulsestudios.amongusminecraft.Interface.GameTask;
import com.pandapulsestudios.amongusminecraft.Objects.FixWiring;
import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulsematchmaking.Objects.NetworkRoom;
import org.bukkit.Bukkit;

import java.util.*;

public class GameTaskLibrary {
    public static HashMap<StoredTask, GameTask> GenerateTasks(GameMap gameMap, TaskLength taskLength, NetworkRoom networkRoom, int amount){
        var data = new HashMap<StoredTask, GameTask>();

        if(taskLength == TaskLength.Common) amount = Math.max(1, Math.min(amount, 2));
        if(taskLength == TaskLength.Long) amount = Math.max(1, Math.min(amount, 3));
        if(taskLength == TaskLength.Short) amount = Math.max(1, Math.min(amount, 5));

        while(data.size() < amount){
            var storedTasks = ReturnStoredTaskOfTypeAndMap(gameMap, taskLength);
            var randomIndex = new Random().nextInt(storedTasks.size());
            var randomStoredTask = storedTasks.get(randomIndex);
            if(data.containsKey(randomStoredTask)) continue;
            data.put(randomStoredTask, ReturnGameTaskFromStoredTask(randomStoredTask, networkRoom));
        }

        return data;
    }

    public static List<StoredTask> ReturnStoredTaskOfTypeAndMap(GameMap gameMap, TaskLength taskLength){
        var data = new ArrayList<StoredTask>();
        for(var storedTask : StoredTask.values()){
            if(storedTask.gameMap == gameMap && storedTask.taskLength == taskLength) data.add(storedTask);
        }
        return data;
    }

    private static GameTask ReturnGameTaskFromStoredTask(StoredTask storedTask, NetworkRoom networkRoom){
        if(storedTask == StoredTask.FixWiring){
            var fixWiring = new FixWiring();
            fixWiring.SetupGameTask(networkRoom);
            return fixWiring;
        }
        return null;
    }
}
