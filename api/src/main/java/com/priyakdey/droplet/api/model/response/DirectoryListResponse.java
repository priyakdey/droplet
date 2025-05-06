package com.priyakdey.droplet.api.model.response;

import com.priyakdey.droplet.api.dto.DirectoryDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Priyak Dey
 */
public class DirectoryListResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 3763608901484961951L;

    private List<DirectoryDto> directories;

    public DirectoryListResponse() {
    }

    public DirectoryListResponse(List<DirectoryDto> directories) {
        this.directories = directories;
    }

    public List<DirectoryDto> getDirectories() {
        return directories;
    }

    public void setDirectories(List<DirectoryDto> directories) {
        this.directories = directories;
    }
}
