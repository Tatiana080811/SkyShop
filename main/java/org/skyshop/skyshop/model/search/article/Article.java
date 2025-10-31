package org.skyshop.skyshop.model.search.article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skyshop.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements Searchable {
    private  String name;
    private  String content;
    private final UUID id;

    public Article(String name, String content) {
        this.name = name;
        this.content = content;
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Article)) return false;
        Article other = (Article) obj;
        return Objects.equals(name, other.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    @JsonIgnore
    public String getSearchTerm() {
        return name + " " + content;
    }
    @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Статья: " + name + ". " + content;
    }
}
