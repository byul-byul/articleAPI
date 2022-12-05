package org.burhan;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleService {
    public List<Article> getArticleList(ArticleRepository articleRepository) {
        return articleRepository.findAll();
    }

    public void addArticle(@RequestBody ArticleController.NewArticleRequest request,
                           ArticleRepository articleRepository) {
        Article article = new Article();
        article.setAuthor(request.getAuthor());
        article.setContent(request.getContent());
        article.setTitle(request.getTitle());
        article.setDate(request.getDate());
        articleRepository.save(article);
    }

    public void deleteArticle(@PathVariable("articleId") Long id,
                              ArticleRepository articleRepository) {
        articleRepository.deleteById(id);
    }

    public void updateArticle(@PathVariable("articleId") Long id,
                              @RequestBody Article request,
                              ArticleRepository articleRepository) {
        Article article = articleRepository.getReferenceById(id);
        article.setAuthor(request.getAuthor());
        article.setContent(request.getContent());
        article.setTitle(request.getTitle());
        article.setDate(request.getDate());
        articleRepository.save(article);
    }
}
