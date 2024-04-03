package com.pandapulsestudios.amongusminecraft.Enums;

public enum GameTaskArea {
    Electrical("Electrical"),
    Storage("Storage"),
    Admin("Admin"),
    Navigation("Navigation"),
    Cafeteria("Cafeteria"),
    Security("Security");


    public String areaName;
    GameTaskArea(String areaName){
        this.areaName = areaName;
    }
}
