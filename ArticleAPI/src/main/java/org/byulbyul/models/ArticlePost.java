package org.byulbyul.models;

import java.time.LocalDateTime;

public record ArticlePost(Long id,
                          String author,
                          String content,
                          String title,
                          LocalDateTime date) {
}
