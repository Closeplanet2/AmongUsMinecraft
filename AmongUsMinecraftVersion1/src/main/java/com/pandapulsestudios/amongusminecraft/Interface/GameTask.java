package com.pandapulsestudios.amongusminecraft.Interface;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface GameTask {
    GameTask SetupTask(Location startLocation);
    void StartGameTask(Player player);
    boolean IsPlayerInTask(Player player);
    void EndGameTask(Player player);
}
