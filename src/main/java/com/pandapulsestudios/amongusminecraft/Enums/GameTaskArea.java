package com.pandapulsestudios.amongusminecraft.Enums;

import com.pandapulsestudios.amongusminecraft.Items.VentItems.UpperEngineVentItem;
import com.pandapulsestudios.pulsecore.Items.ItemStackAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum GameTaskArea {

    UpperEngine("Upper Engine", Material.BLUE_CONCRETE),
    UpperReactor("Reactor", Material.BLUE_CONCRETE),
    LowerReactor("Reactor", Material.BLUE_CONCRETE),
    LowerEngine("Lower Engine", Material.BLUE_CONCRETE),
    Security("Security", Material.BLUE_CONCRETE),
    MedBay("MedBay", Material.BLUE_CONCRETE),
    Electrical("Electrical", Material.BLUE_CONCRETE),
    Storage("Storage", Material.BLUE_CONCRETE),
    Cafeteria("Cafeteria", Material.BLUE_CONCRETE),
    Admin("Admin", Material.BLUE_CONCRETE),
    Communications("Communications", Material.BLUE_CONCRETE),
    O2("O2", Material.BLUE_CONCRETE),
    Weapons("Weapons", Material.BLUE_CONCRETE),
    UpperNavigation("Navigation", Material.BLUE_CONCRETE),
    LowerNavigation("Navigation", Material.BLUE_CONCRETE),
    OutsideNavigation("Navigation", Material.BLUE_CONCRETE),
    Shields("Shields", Material.BLUE_CONCRETE);

    public final String areaName;
    public final Material ventMaterial;
    GameTaskArea(String areaName, Material ventMaterial){
        this.areaName = areaName;
        this.ventMaterial = ventMaterial;
    }
}
