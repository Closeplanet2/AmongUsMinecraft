package com.pandapulsestudios.amongusminecraft.Objects;

import com.pandapulsestudios.amongusminecraft.Enums.GameTask;

import java.util.*;

public class PlayerTaskLibrary {
    private HashMap<GameTask, List<String>> listOfSteps = new HashMap<>();
    private final List<GameTask> commonTasks = new ArrayList<>();
    private final List<GameTask> longTasks = new ArrayList<>();;
    private final List<GameTask> shortTasks = new ArrayList<>();;

    public PlayerTaskLibrary(List<GameTask> commonTasks, List<GameTask> longTasks, List<GameTask> shortTasks){
        for(var commonTask : commonTasks){
            GenerateListOfStepsForGameTask(commonTask);
            this.commonTasks.add(commonTask);
        }
        for(var longTask : longTasks){
            GenerateListOfStepsForGameTask(longTask);
            this.longTasks.add(longTask);
        }
        for(var shortTask : shortTasks){
            GenerateListOfStepsForGameTask(shortTask);
            this.commonTasks.add(shortTask);
        }
    }

    private void GenerateListOfStepsForGameTask(GameTask gameTask){
        var data = new ArrayList<String>();
        if(gameTask == GameTask.AlignEngineOutput){
            data.add("Upper Engine");
            data.add("Lower Engine");
        }else if(gameTask == GameTask.CalibrateDistributor){
            data.add("Electrical");
        }else if(gameTask == GameTask.ChartCourse){
            data.add("Navigation");
        }else if(gameTask == GameTask.CleanO2Filter){
            data.add("O2");
        }else if(gameTask == GameTask.CleanVent){
            var randomList = Arrays.asList(new String[]{"Admin", "Cafeteria", "Electrical", "Lower Engine",
            "MedBay", "Navigation", "Reactor", "Security", "Shields", "Upper Engine", "Weapons"});
            var randomIndex = new Random().nextInt(randomList.size());
            data.add(randomList.get(randomIndex));
        }else if(gameTask == GameTask.ClearAsteroids){
            data.add("Weapons");
        }else if(gameTask == GameTask.DivertPower){
            data.add("Electrical");
            var randomList = Arrays.asList(new String[]{"Communications", "Lower Engine", "Navigation",
            "O2", "Security", "Shields", "Upper Engine", "Weapons"});
            var randomIndex = new Random().nextInt(randomList.size());
            data.add(randomList.get(randomIndex));
        }else if(gameTask == GameTask.EmptyChute){
            data.add("O2");
            data.add("Storage");
        }else if(gameTask == GameTask.EmptyGarbage){
            data.add("Cafeteria");
            data.add("Storage");
        }else if(gameTask == GameTask.FixWiring){
            var randomList = Arrays.asList(new String[]{"Electrical", "Storage", "Admin", "Navigation",
            "Cafeteria", "Security"});
            var randomIndex = new Random().nextInt(randomList.size());
            data.add(randomList.get(randomIndex));
        }else if(gameTask == GameTask.FuelEngines){
            data.add("Storage");
            data.add("Upper Engine");
            data.add("Storage");
            data.add("Lower Engine");
        }else if(gameTask == GameTask.InspectSample){
            data.add("MedBay");
        }else if(gameTask == GameTask.PrimeShields){
            data.add("Shields");
        }else if(gameTask == GameTask.StabilizeSteering){
            data.add("Navigation");
        }else if(gameTask == GameTask.StartReactor){
            data.add("Reactor");
        }else if(gameTask == GameTask.SubmitScan){
            data.add("MedBay");
        }else if(gameTask == GameTask.SwipeCard){
            data.add("Admin");
        }else if(gameTask == GameTask.UnlockManifolds){
            data.add("Reactor");
        }else if(gameTask == GameTask.UploadData){
            var randomList = Arrays.asList(new String[]{"Cafeteria", "Communications", "Electrical", "Navigation", "Weapons"});
            var randomIndex = new Random().nextInt(randomList.size());
            data.add(randomList.get(randomIndex));
            data.add("Admin");
        }
        listOfSteps.put(gameTask, data);
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        for(var gameTask : commonTasks) stringBuilder.append(gameTask.taskName).append(String.format("{%s}", listOfSteps.get(gameTask).toString())).append("<SPLIT_LINE>");
        for(var gameTask : longTasks) stringBuilder.append(gameTask.taskName).append(String.format("{%s}", listOfSteps.get(gameTask).toString())).append("<SPLIT_LINE>");
        for(var gameTask : shortTasks) stringBuilder.append(gameTask.taskName).append(String.format("{%s}", listOfSteps.get(gameTask).toString())).append("<SPLIT_LINE>");
        return stringBuilder.toString();
    }
}
