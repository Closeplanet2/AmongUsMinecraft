package com.pandapulsestudios.amongusminecraft.Enums;

public enum StoredTask {
    AlignEngineOutput("Align Engine Output", GameMap.TheSkeld, TaskLength.Long, false),
    CalibrateDistributor("Calibrate Distributor", GameMap.TheSkeld, TaskLength.Short, false),
    ChartCourse("Chart Course", GameMap.TheSkeld, TaskLength.Short, false),
    CleanO2Filter("Clean O2 Filter", GameMap.TheSkeld, TaskLength.Short, false),
    CleanVent("Clean Vent", GameMap.TheSkeld, TaskLength.Short, false),
    ClearAsteroids("Clear Asteroids", GameMap.TheSkeld, TaskLength.Short, true),
    DivertPower("Divert Power", GameMap.TheSkeld, TaskLength.Short, false),
    EmptyChute("Empty Chute", GameMap.TheSkeld, TaskLength.Long, true),
    EmptyGarbage("Empty Garbage", GameMap.TheSkeld, TaskLength.Long, true),
    FixWiring("Fix Wiring", GameMap.TheSkeld, TaskLength.Common, false),
    FuelEngines("Fuel Engines", GameMap.TheSkeld, TaskLength.Long, false),
    InspectSample("Inspect Sample", GameMap.TheSkeld, TaskLength.Long, false),
    PrimeShields("Prime Shields", GameMap.TheSkeld, TaskLength.Short, true),
    StabilizeSteering("Stabilize Steering", GameMap.TheSkeld, TaskLength.Short, false),
    StartReactor("Start Reactor", GameMap.TheSkeld, TaskLength.Long, false),
    SubmitScan("Submit Scan", GameMap.TheSkeld, TaskLength.Long, true),
    SwipeCard("Swipe Card", GameMap.TheSkeld, TaskLength.Common, false),
    UnlockManifolds("Unlock Manifolds", GameMap.TheSkeld, TaskLength.Short, false),
    UploadData("Upload Data", GameMap.TheSkeld, TaskLength.Short, false);

    public final String taskName;
    public final GameMap gameMap;
    public final TaskLength taskLength;
    public final boolean visual;

    StoredTask(String taskName, GameMap gameMap, TaskLength taskLength, boolean visual){
        this.taskName = taskName;
        this.gameMap = gameMap;
        this.taskLength = taskLength;
        this.visual = visual;
    }
}
