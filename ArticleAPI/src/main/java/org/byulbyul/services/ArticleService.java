package org.byulbyul.services;

import org.byulbyul.exceptions.ApiRequestException;
import org.byulbyul.models.ArticlePost;
import org.byulbyul.repositories.ArticleRepository;
import org.byulbyul.models.Article;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private final static int STATISTICS_DAY_COUNT = 7;
    private final static int ARTICLE_NUMBER_IN_PAGE = 5;
    private final static int DEFAULT_PAGE_NUMBER = 0;
    private final static String DEFAULT_STATISTICS_MSG = """
                                count of published articles
                                on daily bases for the %d days:
                                """;
    private final ArticleRepository articleRepository;
    private int isDurationDay(LocalDate date1, LocalDate date2, int dayCount) {
        return Integer.compare(Period.between(date1, date2).getDays(), dayCount);
    }
    private Integer[] calculateArticleCountByDays(List<Article> articleList,
                                                  LocalDateTime curTime) {
        Integer[] calculated = new Integer[STATISTICS_DAY_COUNT];
        for (int i = 0; i < STATISTICS_DAY_COUNT; i++) {
            calculated[i] = 0;
        }
        for (Article article : articleList) {
            if (isDurationDay(article.getDate().toLocalDate(),
                    curTime.toLocalDate(),
                    STATISTICS_DAY_COUNT) >= 0) {
                continue;
            }
            System.out.println();
            for (int i = 0; i < STATISTICS_DAY_COUNT; i++) {
                if (isDurationDay(article.getDate().toLocalDate(),
                        curTime.toLocalDate(), i) == 0) {
                    calculated[i]++;
                    break;
                }
            }
        }
        return calculated;
    }
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }
    public ResponseEntity<Map<String, Object>> getPagedArticleList(int page) {
        page--;
        Pageable paging = PageRequest.of(page, ARTICLE_NUMBER_IN_PAGE);
        return getMapResponseEntity(paging);
    }
    public ResponseEntity<Map<String, Object>> getPagedArticleList() {
        Pageable paging = PageRequest.of(DEFAULT_PAGE_NUMBER, ARTICLE_NUMBER_IN_PAGE);
        return getMapResponseEntity(paging);
    }
    public String getArticleCountByCertainDays() {
        List<Article> articleListForSort = this.getArticleList();
        LocalDateTime currentDateTime = LocalDateTime.now();
        StringBuilder msgTail = new StringBuilder();
        Integer[] articleCountByDays = calculateArticleCountByDays(
                                        articleListForSort,
                                        currentDateTime);
        int count = 0;

        for (int i = 0; i < STATISTICS_DAY_COUNT; i++) {
            msgTail.append("at ").append(currentDateTime
                                .toLocalDate().minusDays(i))
                                .append(" was published: ")
                                .append(articleCountByDays[i])
                                .append("\n");
            count += articleCountByDays[i];
        }
        msgTail.append("total count of published articles: ").append(count);
        return String.format(DEFAULT_STATISTICS_MSG, STATISTICS_DAY_COUNT) + msgTail;
    }
    private ResponseEntity<Map<String, Object>> getMapResponseEntity(Pageable paging) {
        List<Article> articlList;
        Page<Article> pagedArticles = articleRepository.findAll(paging);
        if (pagedArticles.getTotalPages() < pagedArticles.getNumber() + 1) {
            throw new ApiRequestException("total pages count = " + pagedArticles.getTotalPages());
        }
        articlList = pagedArticles.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articlList);
        response.put("currentPage", pagedArticles.getNumber() + 1);
        response.put("totalItems", pagedArticles.getTotalElements());
        response.put("totalPages", pagedArticles.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public Long addArticle(@RequestBody ArticlePost request) {
        Article article = new Article();
        article.setAuthor(request.author());
        article.setContent(request.content());
        article.setTitle(request.title());
        article.setDate(request.date());
        return articleRepository.save(article).getId();
    }
    public void deleteArticle(@PathVariable("articleId") Long id) {
        articleRepository.deleteById(id);
    }
    public void updateArticle(@PathVariable("articleId") Long id,
                              @RequestBody Article request) {
        Article article = articleRepository.getReferenceById(id);
        article.setId(id);
        article.setAuthor(request.getAuthor());
        article.setContent(request.getContent());
        article.setTitle(request.getTitle());
        article.setDate(request.getDate());
        articleRepository.save(article);
    }
}
