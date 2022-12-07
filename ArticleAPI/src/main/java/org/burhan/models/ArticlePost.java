package org.burhan.models;

import java.time.LocalDateTime;

public class ArticlePost {
    private final Long id;
    private final String author;
    private final String content;
    private final String title;
    private final LocalDateTime date;

    public ArticlePost(Long id, String author, String content, String title, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.title = title;
        this.date = date;
    }
    public Long getId() {
        return id;
    }
    public String getAuthor() {
        return author;
    }
    public String getContent() {
        return content;
    }
    public String getTitle() {
        return title;
    }
    public LocalDateTime getDate() {
        return date;
    }
}
