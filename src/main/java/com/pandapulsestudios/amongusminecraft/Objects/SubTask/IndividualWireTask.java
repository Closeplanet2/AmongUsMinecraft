package com.pandapulsestudios.amongusminecraft.Objects.SubTask;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.amongusminecraft.Enums.WireColor;
import com.pandapulsestudios.amongusminecraft.Enums.WirePortType;
import com.pandapulsestudios.amongusminecraft.Libraries.LineDrawing;
import com.pandapulsestudios.pulsecore.Data.API.UUIDDataAPI;
import com.pandapulsestudios.pulsecore.Player.Enums.PlayerAction;
import com.pandapulsestudios.pulsecore.Player.PlayerAPI;
import com.pandapulsestudios.pulsecore.Player.VanishAPI;
import com.pandapulsestudios.pulsematchmaking.Objects.NetworkRoom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IndividualWireTask {

    private final HashMap<Location, WirePortType> wirePortTypes;
    private final Location taskLocation;
    private final Location screenLocationA;
    private final Location screenLocationB;
    private final Vector baseInputOffsetVector = new Vector(4.0, 3.0, 6.5);
    private final Vector baseOutputOffsetVector = new Vector(-4.0, 3.0, 6.5);
    private final Vector screenLocationAOffsetVector = new Vector(-3.0, 3.0, 6.5);
    private final Vector screenLocationBOffsetVector = new Vector(3.0, -3.0, 6.5);
    private Player currentPlayer;
    private Location lastLocation;
    private LineDrawing lineDrawing;
    private int wiresComplete;

    public IndividualWireTask(Location taskLocation, List<WireColor> inputColors, List<WireColor> outputColors){
        this.taskLocation = taskLocation;

        var wirePortTypes = new HashMap<Location, WirePortType>();
        for(var index = 0; index < inputColors.size(); index++){
            var inputColor = inputColors.get(index);
            var inputLocation = this.taskLocation.clone().add(baseInputOffsetVector).subtract(new Vector(0, index * 2, 0));
            inputLocation.getBlock().setType(inputColor.wireMaterial);
            wirePortTypes.put(inputLocation, WirePortType.INPUT);

            var outputColor = outputColors.get(index);
            var outputLocation = this.taskLocation.clone().add(baseOutputOffsetVector).subtract(new Vector(0, index * 2, 0));
            outputLocation.getBlock().setType(outputColor.wireMaterial);
            wirePortTypes.put(outputLocation, WirePortType.OUTPUT);
        }
        this.wirePortTypes = wirePortTypes;

        screenLocationA = this.taskLocation.clone().add(screenLocationAOffsetVector);
        screenLocationB = this.taskLocation.clone().add(screenLocationBOffsetVector);
    }

    public void StartTaskForPlayer(NetworkRoom networkRoom, Player player){
        currentPlayer = player;
        lastLocation = currentPlayer.getLocation();
        wiresComplete = 0;
        currentPlayer.teleport(taskLocation);
        for(var viewer : networkRoom.ReturnAllPlayers()) VanishAPI.HideTargetFromView(player, viewer);
    }

    public void EndTaskForPlayer(NetworkRoom networkRoom, Player player){
        player.teleport(lastLocation);
        for(var viewer : networkRoom.ReturnAllPlayers()) VanishAPI.ShowTargetToViewer(player, viewer);
    }

    public boolean PlayerInteractEvent(PlayerInteractEvent event, NetworkRoom networkRoom, Player player){
        var a = GetTargetBlock(player, 100).getLocation().toVector();
        for(var b : wirePortTypes.keySet()){
            if(a.distance(b.toVector()) < 0.75){
                var wirePortType = wirePortTypes.get(b);
                var bLocation = new Location(player.getWorld(), b.getX(), b.getY(), b.getZ());
                if(lineDrawing == null && wirePortType == WirePortType.INPUT){
                    lineDrawing = new LineDrawing(bLocation, bLocation.getBlock().getType(), Material.BLACK_CONCRETE, screenLocationA, screenLocationB);
                    return false;
                }else if(lineDrawing != null && wirePortType == WirePortType.OUTPUT && lineDrawing.drawMaterial == bLocation.getBlock().getType()){
                    lineDrawing.StopDrawing(false);
                    lineDrawing = null;
                    wiresComplete += 1;
                    if(wiresComplete >= 4) return true;
                }
            }
        }
        return false;
    }

    public void GameLoop(NetworkRoom networkRoom, Player player, int timeElapsed, int timeToReach, boolean reachedEnd) {
        if(lineDrawing == null || currentPlayer == null) return;
        player.sendMessage("Drawing....");
        lineDrawing.DrawLastPointToNewPoint(GetTargetBlock(currentPlayer, 100).getLocation(), true);
    }

    private Block GetTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR || lastBlock.getType() == Material.BARRIER) {
                continue;
            }
            break;
        }
        return lastBlock;
    }


}
