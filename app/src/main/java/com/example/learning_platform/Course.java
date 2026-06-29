package com.example.learning_platform;

import com.google.gson.annotations.SerializedName;

public class Course {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("content")
    private String content;

    // Constructor for adding new course
    public Course(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    // Constructor for updating existing course
    public Course(int id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getContent() { return content; }

    public void setId(int id) { this.id = id; }
}