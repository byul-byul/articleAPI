package org.burhan.controllers;

import org.burhan.models.Article;
import org.burhan.repositories.ArticleRepository;
import org.burhan.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final int COMPARE_MAX_DAY_COUNT = 7;
    private final int COMPARE_MIN_DAY_COUNT = 0;
    private final String DEFAULT_STATISTICS_MSG =   "Count of published articles " +
                                                    "on daily bases for the " +
                                                    COMPARE_MAX_DAY_COUNT +
                                                    " days:\n";
    private final ArticleService articleService;
    public record NewArticleRequest(String author,
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
    @GetMapping("/all")
    public List<Article> getArticleList() {
        return articleService.getArticleList();
    }
    @GetMapping("{pageNumber}")
    public ResponseEntity<Map<String, Object>> getPagedArticleList(@PathVariable("pageNumber") int page) {
        return articleService.getPagedArticleList(page);
    }
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getPagedArticleList() {
        return articleService.getPagedArticleList();
    }
    @GetMapping("/statistics")
    public String getArticleCount() {
        List<Article> articleListForSort = articleService.getArticleList();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Integer[] articleCountByDays = new Integer[COMPARE_MAX_DAY_COUNT];
        String msgTail = "";
        int count = 0;
//        for (Article article : articleListForSort) {
//            if (Duration.between(article.getDate(), currentDateTime).toDays()
//                    >= COMPARE_MIN_DAY_COUNT
//                    && Duration.between(article.getDate(), currentDateTime).toDays()
//                    < COMPARE_MAX_DAY_COUNT) {
//                count++;
//            }
//        }
//        return "" + count;
        for (int i = 0; i < COMPARE_MAX_DAY_COUNT; i++) {
            articleCountByDays[i] = 0;
        }
        for (Article article : articleListForSort) {
            for (int i = 0; i < COMPARE_MAX_DAY_COUNT; i++){
                if (Duration.between(article.getDate(), currentDateTime).toDays() == i) {
                    articleCountByDays[i]++;
                    count++;
                    break ;
                }
            }
        }
        for (int i = 0; i < COMPARE_MAX_DAY_COUNT; i++) {
            msgTail += "at " + currentDateTime.toLocalDate().minusDays(i)
                        + " was published: "
                        + articleCountByDays[i] + "\n";
        }
        msgTail += "total count of published articles: " + count;
        return (DEFAULT_STATISTICS_MSG + msgTail);
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
