package com.priyakdey.droplet.api.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
@Document(collection = "files")
public class File extends Inode {
    @Serial
    private static final long serialVersionUID = -1501130613553809700L;

    private String mimeType;

    private String blobUrl;

    private long size;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getBlobUrl() {
        return blobUrl;
    }

    public void setBlobUrl(String blobUrl) {
        this.blobUrl = blobUrl;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
