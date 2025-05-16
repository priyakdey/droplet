package com.priyakdey.droplet.api.model.response.v1;

import com.priyakdey.droplet.api.model.dto.v1.DirectoryDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Priyak Dey
 */
public class AllDirectoriesResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -3451982220853687672L;

    private List<DirectoryDto> directories;

    public List<DirectoryDto> getDirectories() {
        return directories;
    }

    public void setDirectories(List<DirectoryDto> directories) {
        this.directories = directories;
    }
}
