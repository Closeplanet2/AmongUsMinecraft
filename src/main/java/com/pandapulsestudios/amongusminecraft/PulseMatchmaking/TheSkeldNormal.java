package com.pandapulsestudios.amongusminecraft.PulseMatchmaking;

import com.pandapulsestudios.amongusminecraft.Enums.*;
import com.pandapulsestudios.amongusminecraft.Interface.GameTask;
import com.pandapulsestudios.amongusminecraft.Libraries.GameTaskLibrary;
import com.pandapulsestudios.amongusminecraft.Objects.VentMap;
import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulsecore.Player.Enums.PlayerAction;
import com.pandapulsestudios.pulsecore.Player.PlayerAPI;
import com.pandapulsestudios.pulsecore.Player.TitleAPI;
import com.pandapulsestudios.pulsecore.Player.VanishAPI;
import com.pandapulsestudios.pulsematchmaking.API.LobbyAPI;
import com.pandapulsestudios.pulsematchmaking.Enum.CreateRoomResponse;
import com.pandapulsestudios.pulsematchmaking.Enum.GameLobbyType;
import com.pandapulsestudios.pulsematchmaking.Enum.JoinRandomRoomResponse;
import com.pandapulsestudios.pulsematchmaking.Interfaces.CustomLobby;
import com.pandapulsestudios.pulsematchmaking.Interfaces.LobbyCallbacks;
import com.pandapulsestudios.pulsematchmaking.Objects.GameWorld;
import com.pandapulsestudios.pulsematchmaking.Objects.NetworkRoom;
import com.pandapulsestudios.pulsematchmaking.Objects.RoomOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@CustomLobby(lobbyName = "TheSkeldNormal", minPlayers = 1, maxPlayers = 2, playerTtl = -1, roomTtl = -1, numberOfPlayersPerTeam = 1)
public class TheSkeldNormal implements LobbyCallbacks {

    public static void JoinRandomRoom(Player... players){ LobbyAPI.JoinRandomRoom("TheSkeldNormal", false, true, players); }

    @Override
    public String DefaultGameWorldName() { return "world"; }

    @Override
    public String BaseGameWorldName() { return "AmongUSMinecraft"; }

    @Override
    public String NewGameWorldName(String s) { return String.format("AmongUSMinecraft_%s", s); }

    @Override
    public GameLobbyType GameLobbyType() { return GameLobbyType.NoLobby; }

    @Override
    public Location DefaultLobbyLocation(GameWorld gameWorld) {
        return new Location(gameWorld.ReturnGameWorld(), -10.132f, 70f, 14.7f, -88.9f, 10.5f);
    }

    @Override
    public GameMode LobbyGameMode() { return GameMode.CREATIVE;}

    @Override
    public List<Location> SpawnLocations(GameWorld gameWorld) {
        var data = new ArrayList<Location>();
        data.add(new Location(gameWorld.ReturnGameWorld(), 8.513f, 19f, 4.4f, 2.4f, 1.1f));
        return data;
    }

    @Override
    public void GameLoop(NetworkRoom networkRoom, int timeElapsed, int timeToReach, boolean timeReached) {
        for(var player : networkRoom.ReturnAllPlayers()){
            var playerTasks = (HashMap<TaskLength, HashMap<StoredTask, GameTask>>) networkRoom.GetRoomProperty(GameKeys.PLAYER_TASKS(player), new HashMap<>());
            var currentGameTask = CurrentGameTask(playerTasks, player);
            if(currentGameTask != null) currentGameTask.GameLoop(networkRoom, timeElapsed, timeToReach, timeReached);
        }
    }

    @Override
    public void JoinRandomRoomFailed(JoinRandomRoomResponse joinRandomRoomResponse, RoomOptions roomOptions, Player... players) { CreateRoom(players); }

    @Override
    public void CreateRoomFailed(CreateRoomResponse createRoomResponse, RoomOptions roomOptions, Player... players) { CreateRoom(players); }

    @Override
    public void CreateRoomSuccess(NetworkRoom networkRoom, Player... players) {

    }

    @Override
    public void PlayerJoinedRoom(NetworkRoom networkRoom, Player... players) {
        for(var player : players){
            var tempMessage = "#51F4F7[#51F774+#51F4F7] #5163F7Player has joined the game #51F774%s #51F4F7[#51F774%d#000000/#51F774%d#51F4F7]";
            var joinMessage = String.format(tempMessage, player.getDisplayName(), networkRoom.NumberOfPlayersInRoom(), 2);
            networkRoom.BroadCastMessage(joinMessage);
        }
        if(networkRoom.MaxPlayersReached()) networkRoom.StartLobbyCountdown(1);
    }

    @Override
    public void PlayerLeftRoom(NetworkRoom networkRoom, Player player) {
        var tempMessage = "#51F4F7[#FE5454-#51F4F7] #5163F7Player has joined the game #51F774%s #51F4F7[#51F774%d#000000/#51F774%d#51F4F7]";
        var joinMessage = String.format(tempMessage, player.getDisplayName(), networkRoom.NumberOfPlayersInRoom(), 2);
        networkRoom.BroadCastMessage(joinMessage);
    }

    @Override
    public void LobbyTimerCountdown(NetworkRoom networkRoom, int i, boolean b) {
        var tempMessage = "#51F774Game Starting... #51F4F7(#51F774%d#51F4F7)";
        networkRoom.BroadCastMessage(String.format(tempMessage, i));
        if(!b) return;
        networkRoom.StartSpawnSequence(1, GameMode.CREATIVE);
    }

    @Override
    public void SpawnTimerCountdown(NetworkRoom networkRoom, int i, boolean b) {
        var tempMessage = "#51F774Game Starting... #51F4F7(#51F774%d#51F4F7)";
        networkRoom.BroadCastMessage(String.format(tempMessage, i));
        if(!b) return;

        var randomPlayer = networkRoom.ReturnRandomPlayer(false);
        networkRoom.SetRoomProperty(GameKeys.TRAITOR_KEY, randomPlayer.getUniqueId());

        for(var player : networkRoom.ReturnAllPlayers()){
            var traitorID = (UUID) networkRoom.GetRoomProperty(GameKeys.TRAITOR_KEY, null);
            if(player.getUniqueId() == traitorID) TitleAPI.SendTitleToPlayer(player, ChatColor.RED + "IMPOSTER", "", 0, 1, 0);
            else TitleAPI.SendTitleToPlayer(player, ChatColor.GREEN + "CREW", "", 0, 1, 0);
        }

        var commonTasks = GameTaskLibrary.GenerateTasks(GameMap.TheSkeld, TaskLength.Common, networkRoom, 10);
        for(var player : networkRoom.ReturnAllPlayers()){
            var hashMap = new HashMap<TaskLength, HashMap<StoredTask, GameTask>>();

            for(var storedTask : commonTasks.keySet()){
                var gameTask = commonTasks.get(storedTask);
                if(gameTask != null) gameTask.SetupGameTaskForPlayer(networkRoom, player);
            }
            hashMap.put(TaskLength.Common, commonTasks);

            var longTasks = GameTaskLibrary.GenerateTasks(GameMap.TheSkeld, TaskLength.Long, networkRoom, 10);
            for(var storedTask : longTasks.keySet()){
                var gameTask = commonTasks.get(storedTask);
                if(gameTask != null) gameTask.SetupGameTaskForPlayer(networkRoom, player);
            }
            hashMap.put(TaskLength.Long, longTasks);

            var shortTasks = GameTaskLibrary.GenerateTasks(GameMap.TheSkeld, TaskLength.Short, networkRoom, 10);
            for(var storedTask : longTasks.keySet()){
                var gameTask = commonTasks.get(storedTask);
                if(gameTask != null) gameTask.SetupGameTaskForPlayer(networkRoom, player);
            }
            hashMap.put(TaskLength.Short, shortTasks);

            networkRoom.SetRoomProperty(GameKeys.PLAYER_TASKS(player), hashMap);
        }

        for(var player : networkRoom.ReturnAllPlayers()){
            player.getInventory().clear();
            for(var viewer : networkRoom.ReturnAllPlayers()){
                VanishAPI.ShowTargetToViewer(viewer, player);
                VanishAPI.ShowTargetToViewer(player, viewer);
            }
        }

        networkRoom.SetRoomProperty(GameKeys.VENT_MAP_KEY, VentMap.CreateVentMap(GameMap.TheSkeld));
        networkRoom.StartGame(GameMode.CREATIVE, -1, 1);
    }

    @Override
    public boolean BlockBreakEvent(BlockBreakEvent event, NetworkRoom networkRoom, Player player) {
        return false;
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event, NetworkRoom networkRoom, Player player) {
        var playerTasks = (HashMap<TaskLength, HashMap<StoredTask, GameTask>>) networkRoom.GetRoomProperty(GameKeys.PLAYER_TASKS(player), new HashMap<>());
        var currentGameTask = CurrentGameTask(playerTasks, player);

        if(currentGameTask != null) return currentGameTask.PlayerInteractEvent(event, networkRoom, player);

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null){
            for(var taskLength : playerTasks.keySet()){
                for(var storedTask : playerTasks.get(taskLength).keySet()){
                    var gameTask = playerTasks.get(taskLength).get(storedTask);
                    if(gameTask == null) continue;
                    if(gameTask.TryAndStartGameTask(networkRoom, player, TaskStartType.CLICK_TO_START, event.getClickedBlock().getLocation())) return true;
                }
            }

            var ventMap = (VentMap) networkRoom.GetRoomProperty(GameKeys.VENT_MAP_KEY, VentMap.CreateVentMap(GameMap.TheSkeld));
            if(ventMap.TryAndClickVent(networkRoom, event.getClickedBlock().getLocation().toVector(), player)) return true;
        }

        return true;
    }

    @Override
    public boolean PlayerToggleSneakEvent(PlayerToggleSneakEvent event, NetworkRoom networkRoom, Player player) {
        var ventMap = (VentMap) networkRoom.GetRoomProperty(GameKeys.VENT_MAP_KEY, VentMap.CreateVentMap(GameMap.TheSkeld));
        if(ventMap.TryAndExitVent(networkRoom, player)) return true;

        var playerTasks = (HashMap<TaskLength, HashMap<StoredTask, GameTask>>) networkRoom.GetRoomProperty(GameKeys.PLAYER_TASKS(player), new HashMap<>());
        var currentGameTask = CurrentGameTask(playerTasks, player);
        if(currentGameTask != null) currentGameTask.EndGameTask(networkRoom, player, true);

        return true;
    }

    @Override
    public boolean PlayerDropItemEvent(PlayerDropItemEvent event, NetworkRoom networkRoom, Player player) {
        var ventMap = (VentMap) networkRoom.GetRoomProperty(GameKeys.VENT_MAP_KEY, VentMap.CreateVentMap(GameMap.TheSkeld));
        var state = ventMap.TryAndDropItem(player);
        return true;
    }

    private GameTask CurrentGameTask(HashMap<TaskLength, HashMap<StoredTask, GameTask>> playerTasks, Player player){
        for(var taskLength : playerTasks.keySet()){
            for(var storedTask : playerTasks.get(taskLength).keySet()){
                var gameTask = playerTasks.get(taskLength).get(storedTask);
                if(gameTask == null) continue;
                if(gameTask.IsDoingTask(player)) return gameTask;
            }
        }
        return null;
    }

    private void CreateRoom(Player... players){
        var roomOptions = RoomOptions.Create("TheSkeldNormal", new HashMap<>(), true, true, null);
        LobbyAPI.CreateRoomAndJoin("TheSkeldNormal", UUID.randomUUID().toString(), roomOptions, false, true, players);
    }
}
