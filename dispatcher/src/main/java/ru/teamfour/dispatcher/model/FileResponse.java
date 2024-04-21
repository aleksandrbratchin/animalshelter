package ru.teamfour.dispatcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileResponse {

    @JsonProperty("ok")
    private boolean ok;

    @JsonProperty("result")
    private FileResult result;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public FileResult getResult() {
        return result;
    }

    public void setResult(FileResult result) {
        this.result = result;
    }
}