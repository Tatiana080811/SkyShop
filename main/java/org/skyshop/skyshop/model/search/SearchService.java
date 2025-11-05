package org.skyshop.skyshop.model.search;

import org.skyshop.skyshop.model.search.article.Article;
import org.skyshop.skyshop.model.search.product.Product;
import org.skyshop.skyshop.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.skyshop.skyshop.model.search.SearchResult;
import org.skyshop.skyshop.model.search.Searchable;

@Service
public class SearchService {
    private final StorageService storageService;

    @Autowired
    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<SearchResult> search(String pattern) {
        List<Searchable> searchables = (List<Searchable>) storageService.getAllSearchables();
        return searchables.stream()
                .filter(s -> s.getName().contains(pattern))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }

}
