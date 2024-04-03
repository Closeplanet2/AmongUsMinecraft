package com.pandapulsestudios.amongusminecraft.Libraries;

import com.pandapulsestudios.pulsecore.Location.LocationAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LineDrawing {
    private List<Location> points = new ArrayList<>();
    private HashMap<Location, Material> lastBuffer = new HashMap<>();
    private final Location drawingAreaA;
    private final Location drawingAreaB;
    public Material drawMaterial;
    private Material screenMaterial;
    private boolean canDraw = false;

    public LineDrawing(Location startLocation, Material drawMaterial, Material screenMaterial, Location drawingAreaA, Location drawingAreaB){
        this.drawingAreaA = drawingAreaA;
        this.drawingAreaB = drawingAreaB;
        this.drawingAreaA.getBlock().setType(Material.BLUE_CONCRETE);
        this.drawingAreaB.getBlock().setType(Material.BLUE_CONCRETE);
        AddPoint(startLocation);
        SetDrawMaterial(drawMaterial);
        SetScreenMaterial(screenMaterial);
        canDraw = true;
    }

    public void SetDrawMaterial(Material drawMaterial){
        this.drawMaterial = drawMaterial;
    }
    public void SetScreenMaterial(Material screenMaterial){
        this.screenMaterial = screenMaterial;
    }

    public void DrawLastPointToNewPoint(Location location, boolean wipeScreenOnNewFrame){
        if(!canDraw) return;
        if(wipeScreenOnNewFrame) ClearScreen(false);
        var lastLocation = points.get(points.size() - 1);
        for(var point : ReturnAllLocationsBetweenTwoLocations(location, lastLocation, 1)){
            if(IsInArea(drawingAreaA, drawingAreaB, point)){
                lastBuffer.put(point, point.getBlock().getType());
                point.getBlock().setType(drawMaterial);
            }
        }
    }

    private boolean IsInArea(Location a, Location b, Location c) {
        double minX = Math.ceil(Math.min(a.getX(), b.getX()));
        double minY = Math.ceil(Math.min(a.getY(), b.getY()));
        double minZ = Math.ceil(Math.min(a.getZ(), b.getZ()));

        double maxX = Math.ceil(Math.max(a.getX(), b.getX()));
        double maxY = Math.ceil(Math.max(a.getY(), b.getY()));
        double maxZ = Math.ceil(Math.max(a.getZ(), b.getZ()));

        double cx = Math.ceil(c.getX());
        double cy = Math.ceil(c.getY());
        double cz = Math.ceil(c.getZ());

        return cx >= minX && cx <= maxX && cy >= minY && cy <= maxY && cz >= minZ && cz <= maxZ;
    }

    public void PauseDrawing(){
        canDraw = false;
    }

    public void StopDrawing(boolean clearDrawing){
        PauseDrawing();
        points.clear();
        if(clearDrawing) ClearScreen(false);
    }

    public void ClearScreen(boolean fullScreenWipe){
        if(fullScreenWipe){
            for(var location : GetAllLocationsInCubeArea(drawingAreaA, drawingAreaB)) location.getBlock().setType(screenMaterial);
        }else{
            for(var point : lastBuffer.keySet()) point.getBlock().setType(lastBuffer.get(point));
        }
    }

    public void AddPoint(Location location){
        points.add(location);
    }

    private Location[] ReturnAllLocationsBetweenTwoLocations(Location start, Location end, double spacing){
        var startVec = start.toVector();
        var endVec = end.toVector();
        var direction = endVec.clone().subtract(startVec).normalize();

        var distance = start.distance(end);
        var numberOfPoints = (int) Math.ceil(distance / spacing);

        var locations = new Location[numberOfPoints];
        for (var i = 0; i < numberOfPoints; i++) {
            Vector pointVec = startVec.clone().add(direction.clone().multiply(spacing * i));
            locations[i] = pointVec.toLocation(start.getWorld());
        }
        return locations;
    }

    private List<Location> GetAllLocationsInCubeArea(Location a, Location b) {
        List<Location> locations = new ArrayList<>();

        int minX = Math.min(a.getBlockX(), b.getBlockX());
        int minY = Math.min(a.getBlockY(), b.getBlockY());
        int minZ = Math.min(a.getBlockZ(), b.getBlockZ());

        int maxX = Math.max(a.getBlockX(), b.getBlockX());
        int maxY = Math.max(a.getBlockY(), b.getBlockY());
        int maxZ = Math.max(a.getBlockZ(), b.getBlockZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    locations.add(new Location(a.getWorld(), x, y, z));
                }
            }
        }

        return locations;
    }
}
