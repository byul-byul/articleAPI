package org.burhan.controllers;

import org.burhan.exceptions.ArticleAPIRequestException;
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
    private final String DEFAULT_EXCEPTION_MSG = """
                    fields: "title", "author", "content", "date"
                    are mandatory and cannot be blank
                    as well as "date" field must be LocalDateTime format""";
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
        try {
            return articleService.getArticleList();
        } catch (Exception e) {
            throw new ArticleAPIRequestException(e.toString() + "\n" + DEFAULT_EXCEPTION_MSG);
        }
    }
    @GetMapping("{pageNumber}")
    public ResponseEntity<Map<String, Object>> getPagedArticleList(@PathVariable("pageNumber") int page) {
        try {
            return articleService.getPagedArticleList(page);
        } catch (Exception e) {
            throw new ArticleAPIRequestException("invalid page number");
        }
    }
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getPagedArticleList() {
        try {
            return articleService.getPagedArticleList();
        } catch (Exception e) {
            throw new ArticleAPIRequestException(e.toString() + "\n" + DEFAULT_EXCEPTION_MSG);
        }
    }
    @GetMapping("/statistics")
    public String getArticleCount() {
        try {
            List<Article> articleListForSort = articleService.getArticleList();
            LocalDateTime currentDateTime = LocalDateTime.now();
            Integer[] articleCountByDays = new Integer[COMPARE_MAX_DAY_COUNT];
            String msgTail = "";
            int count = 0;
            for (int i = 0; i < COMPARE_MAX_DAY_COUNT; i++) {
                articleCountByDays[i] = 0;
            }
            for (Article article : articleListForSort) {
                for (int i = 0; i < COMPARE_MAX_DAY_COUNT; i++) {
                    if (Duration.between(article.getDate(), currentDateTime).toDays() == i) {
                        articleCountByDays[i]++;
                        count++;
                        break;
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
        } catch (Exception e) {
            throw new ArticleAPIRequestException(e.toString() + "\n" + DEFAULT_EXCEPTION_MSG);
        }
    }
    @PostMapping()
    public void addArticle(@RequestBody NewArticleRequest request) {
        try {
            articleService.addArticle(request);
        } catch (Exception e) {
            throw new ArticleAPIRequestException(DEFAULT_EXCEPTION_MSG);
        }
    }
    @DeleteMapping("{articleId}")
    public void deleteArticle(@PathVariable("articleId") Long id) {
        try {
            articleService.deleteArticle(id);
        } catch (Exception e) {
            throw new ArticleAPIRequestException("invalid articleId");
        }
    }
    @PutMapping ("{articleId}")
    public void updateArticle(@PathVariable("articleId") Long id, @RequestBody Article request) {
        try {
            articleService.updateArticle(id, request);
        } catch (Exception e) {
            throw new ArticleAPIRequestException("""
                    invalid articleId or empty value for mandatory field.
                    """ + DEFAULT_EXCEPTION_MSG);
        }
    }
}
