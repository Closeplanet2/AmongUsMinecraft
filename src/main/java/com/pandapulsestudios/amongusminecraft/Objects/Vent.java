package com.pandapulsestudios.amongusminecraft.Objects;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.pulsecore.Data.API.UUIDDataAPI;
import com.pandapulsestudios.pulsecore.Items.ItemStackAPI;
import com.pandapulsestudios.pulsecore.Player.Enums.PlayerAction;
import com.pandapulsestudios.pulsecore.Player.PlayerAPI;
import com.pandapulsestudios.pulsecore.Player.TitleAPI;
import com.pandapulsestudios.pulsecore.Player.VanishAPI;
import com.pandapulsestudios.pulsematchmaking.Objects.NetworkRoom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.util.Arrays;
import java.util.List;

public record Vent(GameTaskArea gameTaskArea, Vector exitVector, Vector2f rotationVector, List<Integer> connectedLocations) {
    public static Vent CreateVent(GameTaskArea gameTaskArea, Vector exitVector, Vector2f rotationVector, Integer... connectedLocations){
        return new Vent(gameTaskArea, exitVector, rotationVector, Arrays.asList(connectedLocations));
    }

    public void TeleportToVent(NetworkRoom networkRoom, VentMap ventMap, Player player){
        TitleAPI.SendTitleToPlayer(player, ChatColor.RED + "Inside Vents", "", 0, 1, 0);
        for(var viewer : networkRoom.ReturnAllPlayers()) VanishAPI.HideTargetFromView(player, viewer);
        SetVent(player, ventMap, this);
    }

    public void ExitVent(NetworkRoom networkRoom, Player player){
        for(var viewer : networkRoom.ReturnAllPlayers()) VanishAPI.ShowTargetToViewer(player, viewer);
        TitleAPI.SendTitleToPlayer(player, ChatColor.GREEN + "Outside Vents", "", 0, 1, 0);
        PlayerAPI.TogglePlayerAction(PlayerAction.PlayerMove, true, player);
        player.getInventory().clear();
    }

    public void DropItem(Player player, VentMap ventMap){
        var ventList = ventMap.ventMap.values().stream().toList();
        var droppedItemIndex = player.getInventory().getHeldItemSlot();
        var connectedVent = ventList.get(connectedLocations.get(droppedItemIndex));
        SetVent(player, ventMap, connectedVent);
    }

    private void SetVent(Player player, VentMap ventMap, Vent currentVent){
        var world = player.getWorld();
        var ventLocation = new Location(world,
                currentVent.exitVector.getX(), currentVent.exitVector.getY(), currentVent.exitVector.getZ(),
                currentVent.rotationVector.x, currentVent.rotationVector.y);
        TitleAPI.SendTitleToPlayer(player, ChatColor.RED + currentVent.gameTaskArea.areaName, "", 0, 1, 0);
        player.teleport(ventLocation);
        SetPlayerHotBar(player, ventMap, currentVent);
        UUIDDataAPI.STORE(player.getUniqueId(), "StoredLocation", player.getLocation());
        PlayerAPI.TogglePlayerAction(PlayerAction.PlayerMove, false, player);
        ventMap.playerPerVent.put(player.getUniqueId(), currentVent);
    }

    private void SetPlayerHotBar(Player player, VentMap ventMap, Vent currentVent){
        var ventList = ventMap.ventMap.values().stream().toList();
        for(var i = 0; i < 9; i++){
            if(i >= currentVent.connectedLocations.size()) player.getInventory().setItem(i, null);
            else{
                var connectedVent = ventList.get(currentVent.connectedLocations.get(i));
                var ventItem = ItemStackAPI.ReturnPulseItem(connectedVent.gameTaskArea.name());
                player.getInventory().setItem(i, ventItem.returnItemStack());
            }
        }
        player.getInventory().setHeldItemSlot(0);
    }
}
