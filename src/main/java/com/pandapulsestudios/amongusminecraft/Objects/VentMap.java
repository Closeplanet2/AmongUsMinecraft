package com.pandapulsestudios.amongusminecraft.Objects;

import com.pandapulsestudios.amongusminecraft.Enums.GameKeys;
import com.pandapulsestudios.amongusminecraft.Enums.GameMap;
import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.pulsematchmaking.Objects.NetworkRoom;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.util.Vector;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class VentMap {

    public static VentMap CreateVentMap(GameMap gameMap){
        var ventMap = new VentMap();
        if(gameMap == GameMap.TheSkeld){
            ventMap.ventMap.put(new Vector(40, 18, 12), Vent.CreateVent(GameTaskArea.UpperEngine, new Vector(40.5, 19, 12.6), new Vector2f(-178.2f, 0.3f), 1));
            ventMap.ventMap.put(new Vector(52, 18, -4), Vent.CreateVent(GameTaskArea.UpperReactor, new Vector(52.7, 19, -3.421), new Vector2f(178.9f, -3.4f),0));

            ventMap.ventMap.put(new Vector(52, 18, -12), Vent.CreateVent(GameTaskArea.LowerReactor, new Vector(52.4, 19, -11.543), new Vector2f(3.3f, 12.1f), 3));
            ventMap.ventMap.put(new Vector(40, 18, -29), Vent.CreateVent(GameTaskArea.LowerEngine, new Vector(40.471, 19, -28.7), new Vector2f(0.9f, 6.5f), 2));

            ventMap.ventMap.put(new Vector(29, 18, -6),Vent.CreateVent(GameTaskArea.MedBay, new Vector(29.5, 19, -6.07), new Vector2f(45.2f, 4.8f), 5, 6));
            ventMap.ventMap.put(new Vector(34, 18, -11),Vent.CreateVent(GameTaskArea.Security, new Vector(34.3, 19, -10.7), new Vector2f(-34.3f, 12.1f), 4, 6));
            ventMap.ventMap.put(new Vector(26, 18, -14),Vent.CreateVent(GameTaskArea.Electrical, new Vector(26.8, 19, -13.256), new Vector2f(128.1f, 6.3f), 4, 5));

            ventMap.ventMap.put(new Vector(-2, 18, 2),Vent.CreateVent(GameTaskArea.Cafeteria, new Vector(-1.2, 19, 2.4), new Vector2f(-49.5f, 8.5f), 8, 9));
            ventMap.ventMap.put(new Vector(2.0, 18, -15.0),Vent.CreateVent(GameTaskArea.Admin, new Vector(2.4, 19, -14.6), new Vector2f(42.0f, 2.3f), 7, 9));
            ventMap.ventMap.put(new Vector(-15, 18, -10),Vent.CreateVent(GameTaskArea.OutsideNavigation, new Vector(-14.2, 19, -9.158), new Vector2f(128.1f, 6.3f), 7, 8));

            ventMap.ventMap.put(new Vector(-13, 18, 12), Vent.CreateVent(GameTaskArea.Weapons, new Vector(-12.5, 19, 12.6), new Vector2f(172.4f, 2.0f), 11));
            ventMap.ventMap.put(new Vector(-29, 18, -3.0), Vent.CreateVent(GameTaskArea.UpperNavigation, new Vector(-28.192, 19.06, -2.5), new Vector2f(-170.7f, 6.0f), 10));

            ventMap.ventMap.put(new Vector(-29, 18, -9), Vent.CreateVent(GameTaskArea.LowerNavigation, new Vector(-28.3, 19, -8.6), new Vector2f(16.6f, 5.9f), 13));
            ventMap.ventMap.put(new Vector(-14, 18, -28), Vent.CreateVent(GameTaskArea.Shields, new Vector(-13.48, 19, -27.5), new Vector2f(-17.7f, 6.2f), 12));
        }
        return ventMap;
    }
    public HashMap<UUID, Vent> playerPerVent = new HashMap<>();

    public LinkedHashMap<Vector, Vent> ventMap = new LinkedHashMap<>();

    public boolean TryAndClickVent(NetworkRoom networkRoom, Vector clickedVector, Player player){
        var traitorID = (UUID) networkRoom.GetRoomProperty(GameKeys.TRAITOR_KEY, UUID.randomUUID());
        if(player.getUniqueId() != traitorID) return false;
        if(!ventMap.containsKey(clickedVector)) return false;
        var currentClickedVent = ventMap.get(clickedVector);
        currentClickedVent.TeleportToVent(networkRoom, this, player);
        return true;
    }

    public boolean TryAndExitVent(NetworkRoom networkRoom, Player player){
        if(!playerPerVent.containsKey(player.getUniqueId())) return false;
        var currentClickedVent = playerPerVent.get(player.getUniqueId());
        currentClickedVent.ExitVent(networkRoom, player);
        playerPerVent.remove(player.getUniqueId());
        return true;
    }

    public boolean TryAndDropItem(Player player){
        if(!playerPerVent.containsKey(player.getUniqueId())) return false;
        var currentClickedVent = playerPerVent.get(player.getUniqueId());
        currentClickedVent.DropItem(player, this);
        return true;
    }



}
