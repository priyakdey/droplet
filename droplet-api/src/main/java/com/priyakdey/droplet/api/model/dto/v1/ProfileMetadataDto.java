package com.priyakdey.droplet.api.model.dto.v1;

/**
 * @author Priyak Dey
 */
public record ProfileMetadataDto(Integer accountId, Integer profileId, String homeDirId,
                                 String name, String timeZone) {
}
