package com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookSearch {
    @JsonProperty("results")
    private List<BookData> results;

    public List<BookData> getResults() {
        return results;
    }

    public void setResults(List<BookData> results) {
        this.results = results;
    }
}
