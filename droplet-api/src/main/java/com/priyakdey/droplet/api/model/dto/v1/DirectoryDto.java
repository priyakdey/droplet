package com.priyakdey.droplet.api.model.dto.v1;

import com.priyakdey.droplet.api.entity.v1.Directory;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * @author Priyak Dey
 */
public record DirectoryDto(String id, String name, Optional<String> parentId,
                           ZonedDateTime createdAt, ZonedDateTime updatedAt) {


    public static DirectoryDto fromDirectory(Directory directory) {
        String id = directory.getId().toHexString();
        String name = directory.getName();
        String _parentId = directory.getParentId() != null
                ? directory.getParentId().toHexString() : null;
        Optional<String> parentId = Optional.ofNullable(_parentId);
        ZonedDateTime createdAt = directory.getCreatedAt();
        ZonedDateTime updatedAt = directory.getUpdatedAt();
        return new DirectoryDto(id, name, parentId, createdAt, updatedAt);
    }
}
