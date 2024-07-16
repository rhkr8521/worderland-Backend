package com.rhkr8521.iccas_question.api.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGPTResponseImageDTO {

    private long created;
    private List<ImageURL> data;

    @Getter
    @Builder
    @NoArgsConstructor
    public static class ImageURL {
        private String url;

        @Builder
        public ImageURL(String url) {
            this.url = url;
        }
    }

    @Builder
    public ChatGPTResponseImageDTO(long created, List<ImageURL> data) {
        this.created = created;
        this.data = data;
    }
}
