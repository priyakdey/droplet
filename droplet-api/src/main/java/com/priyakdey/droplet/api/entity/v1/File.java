package com.priyakdey.droplet.api.entity.v1;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
@Document(collection = "files")
public class File extends Inode implements Serializable {
    @Serial
    private static final long serialVersionUID = 5677311175296611852L;

    private String blobUrl;

    private String mimeType;

    private Integer size;

    public File() {
        super();
    }

    public File(String name, Integer ownerId, String blobUrl, String mimeType, Integer size) {
        super(name, ownerId);
        this.blobUrl = blobUrl;
        this.mimeType = mimeType;
        this.size = size;
    }

    public String getBlobUrl() {
        return blobUrl;
    }

    public void setBlobUrl(String blobUrl) {
        this.blobUrl = blobUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
