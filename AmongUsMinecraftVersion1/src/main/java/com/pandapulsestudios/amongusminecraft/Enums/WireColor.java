package com.pandapulsestudios.amongusminecraft.Enums;

import org.bukkit.Material;

public enum WireColor {
    YELLOW(Material.YELLOW_CONCRETE),
    BLUE(Material.BLUE_CONCRETE),
    RED(Material.RED_CONCRETE),
    PINK(Material.PINK_CONCRETE);

    public Material wireMaterial;
    WireColor(Material wireMaterial){
        this.wireMaterial = wireMaterial;
    }
}
