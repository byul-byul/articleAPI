package org.burhan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
//import java.time.LocalDate;
import java.time.LocalDateTime;
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
                             LocalDateTime date) {
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
        LocalDateTime currentDateTime = LocalDateTime.now();

        int count = 0;
        for (Article article : articleListForSort) {
            if (Duration.between(article.getDate(), currentDateTime).toDays()
                    >= COMPARE_MIN_DAY_COUNT
                    && Duration.between(article.getDate(), currentDateTime).toDays()
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
