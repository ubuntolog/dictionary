package top.alexcloud.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {

    enum QueryState {
        INITIAL,
        RUNNING,
        FAILED,
        FINISHED
    }

    public String id;
    public String text;
    public QueryState state;
    public Long resultsFound;
    public Long lifetime;

    public Query() {
        // Needed by Jackson deserialization
    }

    public Query(String id, String text, QueryState state, Long resultsFound, Long lifetime) {
        this.id = id;
        this.text = text;
        this.state = state;
        this.resultsFound = resultsFound;
        this.lifetime = lifetime;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getText() {
        return text;
    }

    @JsonProperty
    public QueryState getState() {
        return state;
    }

    @JsonProperty
    public Long getResultsFound() {
        return resultsFound;
    }

    @JsonProperty
    public Long getLifetime() {
        return lifetime;
    }

}
