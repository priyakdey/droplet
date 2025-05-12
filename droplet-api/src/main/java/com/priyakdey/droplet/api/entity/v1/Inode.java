package com.priyakdey.droplet.api.entity.v1;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Clock;
import java.time.ZonedDateTime;

/**
 * @author Priyak Dey
 */
public abstract class Inode {

    @MongoId
    private ObjectId id;

    private String name;

    private Integer ownerId;

    private ObjectId parentId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    public Inode() {
    }

    public Inode(String name, Integer ownerId) {
        this.name = name;
        this.ownerId = ownerId;
        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
        this.createdAt = now;
        this.updatedAt = now;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public ObjectId getParentId() {
        return parentId;
    }

    public void setParentId(ObjectId parentId) {
        this.parentId = parentId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
