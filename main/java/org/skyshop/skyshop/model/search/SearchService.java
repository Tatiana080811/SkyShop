package org.skypro.skyshop.model.search;

import org.skypro.skyshop.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

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


