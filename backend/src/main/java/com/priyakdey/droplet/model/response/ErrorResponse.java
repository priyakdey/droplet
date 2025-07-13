package com.priyakdey.droplet.model.response;

/**
 * @author Priyak Dey
 */
public class ErrorResponse {

    private String title;
    private String description;

    public ErrorResponse() {
    }

    public ErrorResponse(String title) {
        this.title = title;
    }

    public ErrorResponse(String title, String description) {
        this(title);
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
