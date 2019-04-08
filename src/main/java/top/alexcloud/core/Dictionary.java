package top.alexcloud.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dictionary {

    public Integer id;
    public String name;
    public String description;
    public String srcLanguage;
    public String trgLanguage;


    public Dictionary() {
        // Needed by Jackson deserialization
    }

    public Dictionary(Integer id, String name,String description, String srcLanguage, String trgLanguage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.srcLanguage = srcLanguage;
        this.trgLanguage = trgLanguage;

    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    @JsonProperty
    public String getSrcLanguage() {
        return srcLanguage;
    }

    @JsonProperty
    public String getTrgLanguage() {
        return trgLanguage;
    }
}
