package top.alexcloud.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Match {

    public String word;
    public String dictionaryName;
    public String meaning;

    public Match() {
        // Needed by Jackson deserialization
    }

    public Match(String word, String dictionaryName, String meaning) {
        this.word = word;
        this.dictionaryName = dictionaryName;
        this.meaning = meaning;
    }

    @JsonProperty
    public String getWord() {
        return word;
    }

    @JsonProperty
    public String getDictionaryName() {
        return dictionaryName;
    }

    @JsonProperty
    public String getMeaning() {
        return meaning;
    }

}
