package com.caffidev.unoone.abstracts;

import java.util.UUID;

public abstract class Entity {
    private UUID uuid;
    public Entity() {this (UUID.randomUUID());}
    public Entity(UUID uuid){
        this.uuid = uuid;
    }
    
    public UUID getUuid() {
        return uuid;
    }
}
