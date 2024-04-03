package com.pandapulsestudios.amongusminecraft;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.amongusminecraft.Enums.GameTaskMap;
import com.pandapulsestudios.amongusminecraft.Enums.GameTaskType;
import com.pandapulsestudios.amongusminecraft.Interface.GameTask;
import com.pandapulsestudios.amongusminecraft.Objects.WireTask;
import com.pandapulsestudios.pulsematchmaking.Objects.GameWorld;
import org.bukkit.Location;

import java.util.*;

public class TaskLibrary {

    public static List<com.pandapulsestudios.amongusminecraft.Enums.GameTask> RefineGameTasksByMapAndType(GameTaskType gameTaskType, GameTaskMap gameTaskMap){
        var data = new ArrayList<com.pandapulsestudios.amongusminecraft.Enums.GameTask>();
        for(var gameTask : com.pandapulsestudios.amongusminecraft.Enums.GameTask.values()){
            if(Arrays.asList(gameTask.gameTaskTypes).contains(gameTaskType) && gameTask.gameTaskMap == gameTaskMap){
                data.add(gameTask);
            }
        }
        return data;
    }

    public static List<com.pandapulsestudios.amongusminecraft.Enums.GameTask> GenerateTasks(GameTaskMap gameTaskMap, GameTaskType gameTaskType, int amount){
        var data = new ArrayList<com.pandapulsestudios.amongusminecraft.Enums.GameTask>();

        if(gameTaskType == GameTaskType.Common) amount = Math.max(1, Math.min(amount, 2));
        if(gameTaskType == GameTaskType.Long) amount = Math.max(1, Math.min(amount, 3));
        if(gameTaskType == GameTaskType.Short) amount = Math.max(1, Math.min(amount, 5));

        while (amount > 0){
            var commonTasks = RefineGameTasksByMapAndType(gameTaskType, gameTaskMap);
            var randomIndex = new Random().nextInt(commonTasks.size());
            var randomTask = commonTasks.get(randomIndex);
            if(data.contains(randomTask)) continue;
            data.add(randomTask);
            amount -= 1;
        }

        return data;
    }

    public static List<GameTask> ReturnWireTasks(GameTaskMap gameTaskMap, GameWorld gameWorld, GameTaskArea gameTaskArea){
        var data = new ArrayList<GameTask>();
        if(gameTaskMap == GameTaskMap.TheSkeld){
            if(gameTaskArea == GameTaskArea.Electrical){
                for(var i = 0; i < 10; i++) data.add(new WireTask().SetupTask(new Location(gameWorld.ReturnGameWorld(), 9.5 -  , 8, 5, 0, 0)));
            }else if(gameTaskArea == GameTaskArea.Storage){
                for(var i = 0; i < 10; i++) data.add(new WireTask().SetupTask(new Location(gameWorld.ReturnGameWorld(), 9.5 - (i * 13), 8, -9, 0, 0)));
            }else if(gameTaskArea == GameTaskArea.Admin){
                for(var i = 0; i < 10; i++) data.add(new WireTask().SetupTask(new Location(gameWorld.ReturnGameWorld(), 9.5 - (i * 13), 8, -23.5, 0, 0)));
            }else if(gameTaskArea == GameTaskArea.Navigation){
                for(var i = 0; i < 10; i++) data.add(new WireTask().SetupTask(new Location(gameWorld.ReturnGameWorld(), 9.5 - (i * 13), 8, -37, 0, 0)));
            }else if(gameTaskArea == GameTaskArea.Cafeteria){
                for(var i = 0; i < 10; i++) data.add(new WireTask().SetupTask(new Location(gameWorld.ReturnGameWorld(), 9.5 - (i * 13), 8, -51, 0, 0)));
            }else if(gameTaskArea == GameTaskArea.Security){
                for(var i = 0; i < 10; i++) data.add(new WireTask().SetupTask(new Location(gameWorld.ReturnGameWorld(), 9.5 - (i * 13), 8, -65, 0, 0)));
            }
        }
        return data;
    }
}
