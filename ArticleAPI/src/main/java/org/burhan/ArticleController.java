package org.burhan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final int COMPARE_MAX_DAY_COUNT = 7;
    private final int COMPARE_MIN_DAY_COUNT = 0;

    record NewArticleRequest(String author,
                             String content,
                             String title,
                             LocalDate date) {
        public String getAuthor() {
            return author;
        }
        public String getContent() {
            return content;
        }
        public String getTitle() {
            return title;
        }
        public LocalDate getDate() {
            return date;
        }
    }
    @Autowired
    public ArticleController(ArticleRepository articleRepository, ArticleService articleService) {
        this.articleService = articleService;
    }
    @GetMapping()
    public List<Article> getArticleList() {
        return articleService.getArticleList();
    }
    @GetMapping("/statistics")
    public int getArticleCount() {
        List<Article> articleListForSort = articleService.getArticleList();
        LocalDate currentDateTime = LocalDate.now();
        int count = 0;
        for (Article article : articleListForSort) {
            if (Duration.between(article.getDate().atStartOfDay(), currentDateTime.atStartOfDay()).toDays()
                    > COMPARE_MIN_DAY_COUNT
                    && Duration.between(article.getDate().atStartOfDay(), currentDateTime.atStartOfDay()).toDays()
                    < COMPARE_MAX_DAY_COUNT) {
                count++;
            }
        }
        return count;
    }
    @PostMapping()
    public void addArticle(@RequestBody NewArticleRequest request) {
        articleService.addArticle(request);
    }
    @DeleteMapping("{articleId}")
    public void deleteArticle(@PathVariable("articleId") Long id) {
        articleService.deleteArticle(id);
    }
    @PutMapping ("{articleId}")
    public void updateArticle(@PathVariable("articleId") Long id, @RequestBody Article request) {
        articleService.updateArticle(id, request);
    }
}
