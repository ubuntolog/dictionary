package top.alexcloud;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class BackendConfiguration extends Configuration {
    @Valid
    @NotNull
    private String version;
    private String dbFullPath;
    private String dbUser;
    private String dbPassword;

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("dbFullPath")
    public String getDbFullPath() {
        return dbFullPath;
    }

    @JsonProperty("dbUser")
    public String getDbUser() {
        return dbUser;
    }

    @JsonProperty("dbPassword")
    public String getDbPassword() {
        return dbPassword;
    }
}
