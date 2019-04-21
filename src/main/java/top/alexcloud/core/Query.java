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
    public String statusMessage;
    public Integer resultsFound;
    public Long modifiedTime;

    public Query() {
        // Needed by Jackson deserialization
    }

    public Query(String id, String text, QueryState state, String statusMessage, Integer resultsFound, Long modifiedTime) {
        this.id = id;
        this.text = text;
        this.state = state;
        this.statusMessage = statusMessage;
        this.resultsFound = resultsFound;
        this.modifiedTime = modifiedTime;
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
    public String getStatusMessage() {
        return statusMessage;
    }

    @JsonProperty
    public Integer getResultsFound() {
        return resultsFound;
    }

    @JsonProperty
    public Long getModifiedTime() {
        return modifiedTime;
    }

}
