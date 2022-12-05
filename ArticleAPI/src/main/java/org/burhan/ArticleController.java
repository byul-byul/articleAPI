package org.burhan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

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

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    @Autowired
    public ArticleController(ArticleRepository articleRepository, ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    @GetMapping()
    public List<Article> getArticleList() {
        return articleService.getArticleList(getArticleRepository());
    }

    @PostMapping()
    public void addArticle(@RequestBody NewArticleRequest request) {
        articleService.addArticle(request, getArticleRepository());
    }

    @DeleteMapping("{articleId}")
    public void deleteArticle(@PathVariable("articleId") Long id) {
        articleService.deleteArticle(id, getArticleRepository());
    }

    @PutMapping ("{articleId}")
    public void updateArticle(@PathVariable("articleId") Long id, @RequestBody Article request) {
        articleService.updateArticle(id, request, getArticleRepository());
    }
}
