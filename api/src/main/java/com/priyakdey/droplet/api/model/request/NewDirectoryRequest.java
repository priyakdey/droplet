package com.priyakdey.droplet.api.model.request;

import org.bson.types.ObjectId;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class NewDirectoryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 2325801830622393243L;

    private String name;
    private String parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
