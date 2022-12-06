package org.burhan;

import jakarta.persistence.*;
//import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;

@Entity
@Table(name="articles")
public class Article {
    @Id
    @SequenceGenerator(
            name = "article_id_sequence",
            sequenceName = "article_id_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "article_id_sequence")
    private Long            id;
    @Column(name="article_title", length=100, nullable=false)
    @Size(min = 1)
    private String          title;
    @Column(name="article_author", nullable=false)
    @Size(min = 1)
    private String          author;
    @Column(name="article_content", columnDefinition="TEXT", nullable=false)
    @Size(min = 1)
    private String          content;
    @Column(name="publsih_date", nullable=false)
    @Size(min = 1)
    private LocalDateTime date;
    public Article() {}
    public Article(String title,
                   String author,
                   String content,
                   LocalDateTime date) {
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

    public LocalDateTime getDate() {
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

    public void setDate(LocalDateTime date) {
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