package it.bz.davinci.innovationscoreboard.utils.rest;

import lombok.Data;

import java.util.Collection;

@Data
public class CollectionResponse<T> {

    private final Collection<T> results;

    public CollectionResponse(Collection<T> results) {
        this.results = results;
    }
}
