package com.sust.bup_project.community;

class Post {
    private String username;
    private String title;
    private String description;
    private String key;

    public Post(String username, String title, String description, String key) {
        this.username = username;
        this.title = title;
        this.description = description;
        this.key = key;
    }

    public Post() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
