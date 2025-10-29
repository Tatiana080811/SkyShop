package org.skypro.skyshop.model.search;

import java.util.UUID;

public final class SearchResult {
    private final String id;
    private final String name;
    private final String contentType;
    public SearchResult(String id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }
    public SearchResult(UUID id, String name, String contentType) {
        this.id = id.toString(); // Преобразуем UUID в строку
        this.name = name;
        this.contentType = contentType;
    }

    public static SearchResult fromSearchable(org.skypro.skyshop.model.search.Searchable searchable) {
        return new SearchResult(
                searchable.getId(),
                searchable.getName(),
                searchable.getContentType()
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}


