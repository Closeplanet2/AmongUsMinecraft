package com.pandapulsestudios.amongusminecraft.Objects;

import com.pandapulsestudios.amongusminecraft.Enums.WireColor;
import com.pandapulsestudios.amongusminecraft.Enums.WirePortType;
import org.bukkit.Location;

public class WireTaskPort {

    private final WireColor wireColor;
    private final WirePortType wirePortType;

    public WireTaskPort(Location location, WireColor wireColor, WirePortType wirePortType){
        this.wireColor = wireColor;
        location.getBlock().setType(this.wireColor.wireMaterial);
        this.wirePortType = wirePortType;
    }
}
