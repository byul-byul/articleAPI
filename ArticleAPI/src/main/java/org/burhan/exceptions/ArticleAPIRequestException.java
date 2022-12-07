package org.burhan.exceptions;
public class ArticleAPIRequestException extends RuntimeException {
    public ArticleAPIRequestException(String message) {
        super(message);
    }
}
