package com.example.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult<T> {

    @JsonInclude(JsonInclude.Include.ALWAYS)
    T result;
    JsonRequestError error;
    public JsonResult() {
    }
    @JsonIgnore
    public JsonResult(JsonRequestError error) {
        this.error = error;
    }

    @JsonIgnore
    public JsonResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public JsonRequestError getError() {
        return error;
    }

    public void setError(JsonRequestError error) {
        this.error = error;
    }

}
