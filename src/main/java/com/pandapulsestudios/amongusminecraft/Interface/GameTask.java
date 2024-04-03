package com.pandapulsestudios.amongusminecraft.Interface;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.amongusminecraft.Enums.TaskStartType;
import com.pandapulsestudios.pulsematchmaking.Objects.NetworkRoom;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public interface GameTask {
    List<GameTaskArea>  MasterAreaList();
    void SetupGameTask(NetworkRoom networkRoom);
    void SetupGameTaskForPlayer(NetworkRoom networkRoom, Player player);
    boolean IsDoingTask(Player player);
    boolean TryAndStartGameTask(NetworkRoom networkRoom, Player player, TaskStartType taskStartType, Location triggerLocation);
    default boolean PlayerInteractEvent(PlayerInteractEvent event, NetworkRoom networkRoom, Player player){ return false; };
    void GameLoop(NetworkRoom networkRoom, int timeElapsed, int timeToReach, boolean reachedEnd);
    void EndGameTask(NetworkRoom networkRoom, Player player, boolean cancelledTask);

}
