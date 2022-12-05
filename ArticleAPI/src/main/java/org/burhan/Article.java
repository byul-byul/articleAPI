package org.burhan;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="articles")
public class Article {
    @Id
    @SequenceGenerator(
            name = "article_id_sequence",
            sequenceName = "article_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "article_id_sequence"
    )
    private Long            id;
    
    @Column(name="article_title", length=100, nullable=false, unique=false)
    private String          title;
    @Column(name="article_author", nullable=false, unique=false)
    private String          author;
    @Column(name="article_content", columnDefinition="TEXT", nullable=false, unique=false)
    private String          content;
    @Column(name="publsih_date", nullable=false, unique=false)
    private LocalDate date;
    public Article() {}
    public Article(String title,
                   String author,
                   String content,
                   LocalDate date) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}