package org.burhan.exceptions;
public class ArticleAPIRequestException extends RuntimeException {
    public ArticleAPIRequestException(String message) {
        super(message);
    }

    public ArticleAPIRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
