package com.pandapulsestudios.amongusminecraft.Enums;

import java.util.Collections;

public enum GameTask {
    AlignEngineOutput("Align Engine Output", GameTaskMap.TheSkeld, GameTaskType.Long),
    CalibrateDistributor("Calibrate Distributor", GameTaskMap.TheSkeld, GameTaskType.Short),
    ChartCourse("Chart Course", GameTaskMap.TheSkeld, GameTaskType.Short),
    CleanO2Filter("Clean O2 Filter", GameTaskMap.TheSkeld, GameTaskType.Short),
    CleanVent("Clean Vent", GameTaskMap.TheSkeld, GameTaskType.Short),
    ClearAsteroids("Clear Asteroids", GameTaskMap.TheSkeld, GameTaskType.Short, GameTaskType.Visual),
    DivertPower("Divert Power", GameTaskMap.TheSkeld, GameTaskType.Short),
    EmptyChute("Empty Chute", GameTaskMap.TheSkeld, GameTaskType.Long, GameTaskType.Visual),
    EmptyGarbage("Empty Garbage", GameTaskMap.TheSkeld, GameTaskType.Long, GameTaskType.Visual),
    FixWiring("Fix Wiring", GameTaskMap.TheSkeld, GameTaskType.Common),
    FuelEngines("Fuel Engines", GameTaskMap.TheSkeld, GameTaskType.Long),
    InspectSample("Inspect Sample", GameTaskMap.TheSkeld, GameTaskType.Long),
    PrimeShields("Prime Shields", GameTaskMap.TheSkeld, GameTaskType.Short, GameTaskType.Visual),
    StabilizeSteering("Stabilize Steering", GameTaskMap.TheSkeld, GameTaskType.Short),
    StartReactor("Start Reactor", GameTaskMap.TheSkeld, GameTaskType.Long),
    SubmitScan("Submit Scan", GameTaskMap.TheSkeld, GameTaskType.Long, GameTaskType.Visual),
    SwipeCard("Swipe Card", GameTaskMap.TheSkeld, GameTaskType.Common),
    UnlockManifolds("Unlock Manifolds", GameTaskMap.TheSkeld, GameTaskType.Short),
    UploadData("Upload Data", GameTaskMap.TheSkeld, GameTaskType.Short);

    public final String taskName;
    public final GameTaskMap gameTaskMap;
    public final GameTaskType[] gameTaskTypes;

    GameTask(String taskName, GameTaskMap gameTaskMap, GameTaskType... gameTaskTypes){
        this.taskName = taskName;
        this.gameTaskMap = gameTaskMap;
        this.gameTaskTypes = gameTaskTypes;
    }
}
