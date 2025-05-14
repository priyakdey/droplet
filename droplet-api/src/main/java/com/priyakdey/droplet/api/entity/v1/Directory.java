package com.priyakdey.droplet.api.entity.v1;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
@Document(collection = "directories")
public class Directory extends Inode implements Serializable {
    @Serial
    private static final long serialVersionUID = 4770519191552522174L;

    public Directory() {
        super();
    }

    public Directory(String name, Integer ownerId) {
        super(name, ownerId);
    }
}
