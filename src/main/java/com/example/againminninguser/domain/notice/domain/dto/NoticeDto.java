package com.example.againminninguser.domain.notice.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDto {
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public NoticeDto (long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
