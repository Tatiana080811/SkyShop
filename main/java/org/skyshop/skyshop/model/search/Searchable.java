package org.skypro.skyshop.model.search;
import java.util.UUID;

public interface Searchable {
    UUID getId();
    String getName();
    String getContentType();
    default String getStringRepresentation() {
        return getName() + " - " + getContentType();
    }
}