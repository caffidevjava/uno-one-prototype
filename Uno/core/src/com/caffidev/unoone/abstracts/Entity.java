package com.caffidev.unoone.abstracts;

import java.util.UUID;

/** Represents an entity, that has it's own UUID. Helpful for servers.
 * Low chance of duplicate. */
public abstract class Entity {
    private final UUID uuid;
    
    public Entity() { this(UUID.randomUUID()); }
    
    public Entity(UUID uuid) { this.uuid = uuid; }
    
    /** Gets a uuid of this entity */
    public UUID getUuid() {return uuid;}
}
