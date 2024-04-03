package com.pandapulsestudios.amongusminecraft.PulseMatchmaking.Lobbies;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.amongusminecraft.Enums.GameTaskMap;
import com.pandapulsestudios.amongusminecraft.Enums.GameTaskType;
import com.pandapulsestudios.amongusminecraft.Interface.GameTask;
import com.pandapulsestudios.amongusminecraft.Objects.PlayerTaskLibrary;
import com.pandapulsestudios.amongusminecraft.TaskLibrary;
import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.MessageType;
import com.pandapulsestudios.pulsecore.Player.TitleAPI;
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
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@CustomLobby(lobbyName = "TheSkeldNormal", minPlayers = 1, maxPlayers = 2, playerTtl = -1, roomTtl = -1, numberOfPlayersPerTeam = 1)
public class TheSkeldNormal implements LobbyCallbacks {

    public static void JoinRandomRoom(Player... players){ LobbyAPI.JoinRandomRoom("TheSkeldNormal", false, true, players); }

    public HashMap<UUID, GameTask> taskPerPlayer = new HashMap<>();

    @Override
    public String DefaultGameWorldName() { return "world"; }

    @Override
    public String BaseGameWorldName() { return "AmongUSMinecraft"; }

    @Override
    public String NewGameWorldName(String s) {
        return String.format("AmongUSMinecraft_%s", s);
    }

    @Override
    public GameLobbyType GameLobbyType() {
        return GameLobbyType.NoLobby;
    }

    @Override
    public Location DefaultLobbyLocation(GameWorld gameWorld) {
        return new Location(gameWorld.ReturnGameWorld(), -10.132f, 70f, 14.7f, -88.9f, 10.5f);
    }

    @Override
    public GameMode LobbyGameMode() {
        return GameMode.CREATIVE;
    }

    @Override
    public List<Location> SpawnLocations(GameWorld gameWorld) {
        var data = new ArrayList<Location>();
        data.add(new Location(gameWorld.ReturnGameWorld(), 8.513f, 19f, 4.4f, 2.4f, 1.1f));
        return data;
    }

    @Override
    public void JoinRandomRoomFailed(JoinRandomRoomResponse joinRandomRoomResponse, RoomOptions roomOptions, Player... players) {
        for(var player : players) player.sendMessage(joinRandomRoomResponse.error);
        CreateRoom(players);
    }

    @Override
    public void CreateRoomFailed(CreateRoomResponse createRoomResponse, RoomOptions roomOptions, Player... players) {
        for(var player : players) player.sendMessage(createRoomResponse.error);
        CreateRoom(players);
    }

    @Override
    public void CreateRoomSuccess(NetworkRoom networkRoom, Player... players) {}

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
        if(b) networkRoom.StartSpawnSequence(1, GameMode.CREATIVE);
    }

    @Override
    public void SpawnTimerCountdown(NetworkRoom networkRoom, int i, boolean b) {
        var tempMessage = "#51F774Game Starting... #51F4F7(#51F774%d#51F4F7)";
        networkRoom.BroadCastMessage(String.format(tempMessage, i));
        if(!b) return;
        networkRoom.StartGame(GameMode.CREATIVE);

        var randomPlayer = networkRoom.ReturnRandomPlayer(false);
        networkRoom.SetRoomProperty("Traitor", randomPlayer.getUniqueId());

        for(var player : networkRoom.ReturnAllPlayers()){
            var traitorID = (UUID) networkRoom.GetRoomProperty("Traitor", null);
            if(player.getUniqueId() == traitorID) TitleAPI.SendTitleToPlayer(player, ChatColor.RED + "IMPOSTER", "", 0, 40, 40);
            else TitleAPI.SendTitleToPlayer(player, ChatColor.GREEN + "CREW", "", 1, 3, 1);
        }

        var commonTasks = TaskLibrary.GenerateTasks(GameTaskMap.TheSkeld, GameTaskType.Common, 10);
        for(var player : networkRoom.ReturnAllPlayers()){
            var traitorID = (UUID) networkRoom.GetRoomProperty("Traitor", null);
            if(player.getUniqueId() == traitorID) continue;
            var longTasks = TaskLibrary.GenerateTasks(GameTaskMap.TheSkeld, GameTaskType.Long, 10);
            var shortTakss = TaskLibrary.GenerateTasks(GameTaskMap.TheSkeld, GameTaskType.Short, 10);
            var playerTaskLibrary = new PlayerTaskLibrary(commonTasks, longTasks, shortTakss);
            networkRoom.SetRoomProperty(String.format("PlayerTask(%s)", player.getUniqueId()), playerTaskLibrary);
            ChatAPI.SendChat(playerTaskLibrary.toString(), MessageType.PlayerMessageFromPlugin, false, null ,player);
        }
    }

    private void CreateRoom(Player... players){
        var roomOptions = RoomOptions.Create("TheSkeldNormal", new HashMap<>(), true, true, null);
        LobbyAPI.CreateRoomAndJoin("TheSkeldNormal", UUID.randomUUID().toString(), roomOptions, false, true, players);
    }

    @Override
    public boolean BlockBreakEvent(BlockBreakEvent event, NetworkRoom networkRoom, Player player) {
        return true;
    }

    @Override
    public boolean EntityInteractEvent(EntityInteractEvent event, NetworkRoom networkRoom, Player player) {
        if(networkRoom.IsGameEvent()) player.sendMessage(event.getBlock().getLocation().toString());
        return true;
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event, NetworkRoom networkRoom, Player player) {
        if(networkRoom.IsGameEvent() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null){
            player.sendMessage(event.getClickedBlock().getLocation().toString());
            var eventLocation = event.getClickedBlock().getLocation();
            var locationVector = new Vector3d(eventLocation.getX(), eventLocation.getY(), eventLocation.getZ());

            if(taskPerPlayer.containsKey(player.getUniqueId())) return false;

            if(locationVector.equals(new Vector3d(5.0, 20.0, -9.0))) StartWireTask(player, networkRoom, GameTaskArea.Admin);
            else if(locationVector.equals(new Vector3d(25.0, 20.0, -13.0))) StartWireTask(player, networkRoom, GameTaskArea.Electrical);
            else if(locationVector.equals(new Vector3d(11.0, 20.0, -17.0))) StartWireTask(player, networkRoom, GameTaskArea.Storage);
            else if(locationVector.equals(new Vector3d(-29.0, 20.0, -2.0))) StartWireTask(player, networkRoom, GameTaskArea.Navigation);
            else if(locationVector.equals(new Vector3d(19.0, 20.0, 13.0))) StartWireTask(player, networkRoom, GameTaskArea.Cafeteria);
            else if(locationVector.equals(new Vector3d(40.0, 20.0, -7.0))) StartWireTask(player, networkRoom, GameTaskArea.Security);
        }
        return true;
    }

    private void StartWireTask(Player player, NetworkRoom networkRoom, GameTaskArea gameTaskArea){
        ChatAPI.SendChat(String.format("Starting Wire Task In.... (%s)! Sneak To Exit!", gameTaskArea.areaName), MessageType.PlayerMessageFromPlugin, false, null, player);
        var wireTasks = TaskLibrary.ReturnWireTasks(GameTaskMap.TheSkeld, networkRoom.GameWorld(), gameTaskArea);
        var allPlayersInGame = networkRoom.ReturnAllPlayers();
        var ourPlayerIndex = allPlayersInGame.indexOf(player);
        var ourWireTask = wireTasks.get(ourPlayerIndex);
        ourWireTask.StartGameTask(player);
        taskPerPlayer.put(player.getUniqueId(), ourWireTask);
    }
}
