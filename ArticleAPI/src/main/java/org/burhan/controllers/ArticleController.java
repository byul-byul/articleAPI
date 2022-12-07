package org.burhan.controllers;

import org.burhan.exceptions.ArticleAPIRequestException;
import org.burhan.models.Article;
import org.burhan.models.ArticlePost;
import org.burhan.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;
    private String formResponseMessage(Exception e) {
        String DEFAULT_EXCEPTION_MSG = """
                fields: 'title', 'author', 'content', 'date'
                are mandatory and cannot be blank
                as well as 'date' field must be LocalDateTime format""";
        return DEFAULT_EXCEPTION_MSG + '\n' + e;
    }
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @GetMapping("/all")
    public List<Article> getArticleList() {
        try {
            return articleService.getArticleList();
        } catch (Exception e) {
            throw new ArticleAPIRequestException(e.toString());
        }
    }
    @GetMapping("{pageNumber}")
    public ResponseEntity<Map<String, Object>> getPagedArticleList(@PathVariable("pageNumber") int page) {
        try {
            return articleService.getPagedArticleList(page);
        } catch (Exception exception) {
            throw new ArticleAPIRequestException("invalid page number");
        }
    }
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getPagedArticleList() {
        try {
            return articleService.getPagedArticleList();
        } catch (Exception e) {
            throw new ArticleAPIRequestException(e.toString());
        }
    }
    @GetMapping("/statistics")
    public String getArticleCountByCertainDays() {
        try {
            return articleService.getArticleCountByCertainDays();
        } catch (Exception e) {
            throw new ArticleAPIRequestException(e.toString());
        }
    }
    @PostMapping()
    public void addArticle(@RequestBody ArticlePost request) {
        try {
            articleService.addArticle(request);
        } catch (Exception e) {
            throw new ArticleAPIRequestException(formResponseMessage(e));
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
            throw new ArticleAPIRequestException(formResponseMessage(e));
        }
    }
}
