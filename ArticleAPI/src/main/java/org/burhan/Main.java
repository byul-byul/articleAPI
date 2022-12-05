package org.burhan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1/articles")
public class Main {
    private final ArticleRepository articleRepository;

    public Main(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping()
    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }

    record NewArticleRequest(String author,
                             String content,
                             String title,
                             LocalDate date) {

    }

    @PostMapping()
    public void addArticle(@RequestBody NewArticleRequest request) {
        Article article = new Article();
        article.setAuthor(request.author);
        article.setContent(request.content);
        article.setTitle(request.title);
        article.setDate(request.date);
        articleRepository.save(article);
    }

    @DeleteMapping("{articleId}")
    public void deleteArticle(@PathVariable("articleId") Long id) {
        articleRepository.deleteById(id);
    }

    @PutMapping ("{articleId}")
    public void updateArticle(@PathVariable("articleId") Long id, @RequestBody Article request) {
        Article article = articleRepository.getReferenceById(id);
        article.setAuthor(request.getAuthor());
        article.setContent(request.getContent());
        article.setTitle(request.getTitle());
        article.setDate(request.getDate());
        articleRepository.save(article);
    }
//
//    @GetMapping("/greet")
//    public GreetResponse greet() {
//        GreetResponse response = new GreetResponse(
//                                "Hello world !",
//                                List.of("Java", "Python", "C++"),
//                                new Person("Alex"));
//        return response;
//    }
//
//    record Person(String name) {}
//    record GreetResponse(String greet,
//                         List<String> favProgrammingLanguages,
//                         Person person) {}
//    class GreetResponse {
//        private final String greet;
//
//        public GreetResponse(String greet) {
//            this.greet = greet;
//        }
//
//        public String getGreet() {
//            return greet;
//        }
//
//        @Override
//        public String toString() {
//            return "GreetResponse{" +
//                    "greet='" + greet + '\'' +
//                    '}';
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            GreetResponse that = (GreetResponse) o;
//            return Objects.equals(greet, that.greet);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(greet);
//        }
//    }
}
