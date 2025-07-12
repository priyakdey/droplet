package com.priyakdey.droplet.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Priyak Dey
 */
@Document(value = "profiles")
public class Profile {

    @Id
    private ObjectId id;

    @Field(name = "provider")
    private String provider;

    @Field(name = "provider_id")
    private String providerId;

    @Field(name = "name")
    private String name;

    @Field(name = "email")
    private String email;

    @Field(name = "avatar_url")
    private String avatarUrl;

    public ObjectId getId() {
        return id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
