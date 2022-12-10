package org.byulbyul.services;

import org.byulbyul.controllers.ArticleController;
import org.byulbyul.models.Article;
import org.byulbyul.models.ArticlePost;
import org.byulbyul.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    private ArticleService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ArticleService(articleRepository);
    }
    @Test
    void canGetArticleList() {
        // when
        underTest.getArticleList();
        // then
        verify(articleRepository).findAll();
    }

    @Test
    @Disabled
    void canGetPagedArticleList(int page) {
        canGetArticleCountByCertainDays();
    }

    @Test
    @Disabled
    void canGetPagedArticleList() {
        canGetArticleCountByCertainDays();
    }

    @Test
    @Disabled
    void canGetArticleCountByCertainDays() {
    }

    @Test
    @Disabled
    void canAddArticle() {
//        // given
//        ArticlePost request = new ArticlePost(
//                1L,
//                "test_AUTHOR",
//                "test_CONTENT",
//                "test_TITLE",
//                LocalDateTime.parse("0001-01-01T00:00:00.001")
//        );
//        Article article = new Article();
//        article.setAuthor(request.author());
//        article.setContent(request.content());
//        article.setTitle(request.title());
//        article.setDate(request.date());
//
//        // when
//        underTest.addArticle(request);
//
//        // then
//        ArgumentCaptor<Article> articleArgumentCaptor =
//                ArgumentCaptor.forClass(Article.class);
//        verify(articleRepository)
//                .save(articleArgumentCaptor.capture());
//
//        Article capturedArticle = articleArgumentCaptor.getValue();
//        assertThat(capturedArticle).isEqualTo(article);
    }

    @Test
    void deleteArticle() {
        Long id = 1L;
        // when
        underTest.deleteArticle(id);
        // then
        verify(articleRepository).deleteById(id);
    }

    @Test
    void updateArticle() {
    }
}