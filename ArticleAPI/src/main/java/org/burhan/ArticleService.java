package org.burhan;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    public List<Article> getArticleList() {
        return articleRepository.findAll();
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
