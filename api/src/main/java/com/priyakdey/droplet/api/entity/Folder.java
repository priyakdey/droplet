package com.priyakdey.droplet.api.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
@Document(collection = "folders")
public class Folder extends Inode {
    @Serial
    private static final long serialVersionUID = 5632357020507203193L;

}
