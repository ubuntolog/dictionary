package top.alexcloud.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entry {
    public Integer id;
    public String dictName;
    public Integer dictId;
    public String word;
    public String meaning;

    public Entry() {
        // Needed by Jackson deserialization
    }

    public Entry(Integer id, String dictName, Integer dictId, String word, String meaning) {
        this.id = id;
        this.dictName = dictName;
        this.dictId = dictId;
        this.word = word;
        this.meaning = meaning;
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    @JsonProperty
    public String getDictName() {
        return dictName;
    }

    @JsonProperty
    public Integer getDictId() {
        return dictId;
    }

    @JsonProperty
    public String getWord() {
        return word;
    }

    @JsonProperty
    public String getMeaning() {
        return meaning;
    }
}
