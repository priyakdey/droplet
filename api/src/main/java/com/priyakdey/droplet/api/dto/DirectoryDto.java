package com.priyakdey.droplet.api.dto;

import com.priyakdey.droplet.api.entity.Directory;

import java.time.Instant;

/**
 * @author Priyak Dey
 */
public record DirectoryDto(String id, String name, Long ownerId, String parentId,
                           Instant createdAt, Instant updatedAt) {

    public static DirectoryDto from(Directory dir) {
        return new DirectoryDto(
                dir.getId().toHexString(),
                dir.getName(),
                dir.getOwnerId(),
                dir.getParentId() != null ? dir.getParentId().toHexString() : null,
                dir.getCreatedAt(),
                dir.getUpdatedAt()
        );
    }

}
