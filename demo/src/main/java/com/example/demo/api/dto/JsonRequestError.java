package com.example.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRequestError {
    @JsonProperty(value = "exception", required = true)
    String exception;
    String cause;
    @JsonProperty(value = "errorMessage", required = true)
    String errorMessage;
    String url;
    List<String> stackTrace;

    public JsonRequestError() {
    }

    @JsonIgnore
    public JsonRequestError(Exception e, String url) {
        this.exception = e.getClass().getSimpleName();
        if (e.getCause() != null) {
            this.cause = e.getCause().getClass().getSimpleName();
        }

        this.errorMessage = e.getLocalizedMessage();

        this.stackTrace = new ArrayList<>();
        for (StackTraceElement ste : e.getStackTrace()) {
            stackTrace.add(ste.toString());
        }
        this.url = url;
    }

    @JsonIgnore
    public JsonRequestError(Exception e, String errorMessage, String url) {
        this.exception = e.getClass().getSimpleName();
        if (e.getCause() != null) {
            this.cause = e.getCause().getClass().getSimpleName();
        }

        this.errorMessage = errorMessage;

        this.stackTrace = new ArrayList<>();
        for (StackTraceElement ste : e.getStackTrace()) {
            stackTrace.add(ste.toString());
        }
        this.url = url;
    }

    @JsonIgnore
    public JsonRequestError(String errorMessage, String url) {
        this.errorMessage = errorMessage;
        this.url = url;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }
}
