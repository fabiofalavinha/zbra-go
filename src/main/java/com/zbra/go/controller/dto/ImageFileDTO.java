package com.zbra.go.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageFileDTO {

    @JsonProperty
    private String mediaId;

    @JsonProperty
    private String url;

    @JsonProperty
    private String thumbnailUrl;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
