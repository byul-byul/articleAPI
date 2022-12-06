package org.burhan.services;

import org.burhan.controllers.ArticleController;
import org.burhan.repositories.ArticleRepository;
import org.burhan.models.Article;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ArticleService {
    private final int ARTICLE_NUMBER_IN_PAGE = 5;
    private final int DEFAULT_PAGE_NUMBER = 0;
    private final ArticleRepository articleRepository;
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }
    public ResponseEntity<Map<String, Object>> getPagedArticleList(int page) {
        Pageable paging = PageRequest.of(page, ARTICLE_NUMBER_IN_PAGE);
        return getMapResponseEntity(paging);
    }
    public ResponseEntity<Map<String, Object>> getPagedArticleList() {
        Pageable paging = PageRequest.of(DEFAULT_PAGE_NUMBER, ARTICLE_NUMBER_IN_PAGE);
        return getMapResponseEntity(paging);
    }
    private ResponseEntity<Map<String, Object>> getMapResponseEntity(Pageable paging) {
        List<Article> articlList;
        Page<Article> pagedArticles = articleRepository.findAll(paging);
        articlList = pagedArticles.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articlList);
        response.put("currentPage", pagedArticles.getNumber());
        response.put("totalItems", pagedArticles.getTotalElements());
        response.put("totalPages", pagedArticles.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public void addArticle(@RequestBody ArticleController.NewArticleRequest request) {
        Article article = new Article();
        article.setAuthor(request.getAuthor());
        article.setContent(request.getContent());
        article.setTitle(request.getTitle());
        article.setDate(request.getDate());
        articleRepository.save(article);
    }
    public void deleteArticle(@PathVariable("articleId") Long id) {
        articleRepository.deleteById(id);
    }
    public void updateArticle(@PathVariable("articleId") Long id,
                              @RequestBody Article request) {
        Article article = articleRepository.getReferenceById(id);
        article.setAuthor(request.getAuthor());
        article.setContent(request.getContent());
        article.setTitle(request.getTitle());
        article.setDate(request.getDate());
        articleRepository.save(article);
    }
}
