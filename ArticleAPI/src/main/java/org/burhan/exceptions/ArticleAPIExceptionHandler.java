package org.burhan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ArticleAPIExceptionHandler {
    private final String zoneId = "Europe/Moscow";
    @ExceptionHandler(value = ArticleAPIRequestException.class)
    public ResponseEntity<Object> handleArticleAPIRequestException(ArticleAPIRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ArticleAPIException articleAPIException =
                new ArticleAPIException(
                        e.getMessage(),
                        badRequest,
                        ZonedDateTime.now(ZoneId.of(zoneId))
                );
        return new ResponseEntity<>(articleAPIException, badRequest);
    }
}
