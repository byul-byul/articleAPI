package org.burhan.controllers;

import org.burhan.exceptions.ApiRequestException;
import org.burhan.models.Article;
import org.burhan.models.ArticlePost;
import org.burhan.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    private final ArticleService articleService;
    private final static String DEFAULT_EXCEPTION_MSG = """
                fields:
                - 'title'
                - 'author'
                - 'content'
                - 'date'
                are mandatory and cannot be blank
                as well as 'date' field must be LocalDateTime format""";
    private String formResponseMessage(Exception e) {
        return DEFAULT_EXCEPTION_MSG + '\n' + e.getMessage();
    }
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @GetMapping("/user/articles/all")
    public List<Article> getArticleList() {
        try {
            return articleService.getArticleList();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
    @GetMapping("/user/articles/{pageNumber}")
    public ResponseEntity<Map<String, Object>>
            getPagedArticleList(@PathVariable("pageNumber") int page) {
        try {
            return articleService.getPagedArticleList(page);
        } catch (Exception e) {
            throw new ApiRequestException("invalid page number: " + e.getMessage());
        }
    }
    @GetMapping("/user/articles")
    public ResponseEntity<Map<String, Object>> getPagedArticleList() {
        try {
            return articleService.getPagedArticleList();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
    @GetMapping("/admin/statistics")
    public String getArticleCountByCertainDays() {
        try {
            return articleService.getArticleCountByCertainDays();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
    @PostMapping("/user/articles")
    public String addArticle(@RequestBody ArticlePost request) {
        try {
            return String.format("New article with id=%d was created",
                                articleService.addArticle(request));
        } catch (Exception e) {
            throw new ApiRequestException(formResponseMessage(e));
        }
    }
    @DeleteMapping("/admin/articles/{articleId}")
    public String deleteArticle(@PathVariable("articleId") Long id) {
        try {
            articleService.deleteArticle(id);
            return String.format("Article with id=%d was removed", id);
        } catch (Exception e) {
            throw new ApiRequestException("invalid articleId");
        }
    }
    @DeleteMapping("/admin/articles")
    public void deleteArticle() {
        throw new ApiRequestException("you must provide an articleId");
    }
    @PutMapping ("/user/articles/{articleId}")
    public String updateArticle(@PathVariable("articleId") Long id,
                              @RequestBody Article request) {
        try {
            articleService.updateArticle(id, request);
            return String.format("Article with id=%d was updated", id);
        } catch (Exception e) {
            throw new ApiRequestException(formResponseMessage(e));
        }
    }
    @PutMapping("/user/articles")
    public void updateArticle() {
        throw new ApiRequestException("you must provide an articleId");
    }
}
