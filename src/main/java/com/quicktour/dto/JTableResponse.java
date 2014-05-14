package com.quicktour.dto;


import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author Roman Lukash
 */
public class JTableResponse<E> {
    @JsonProperty("Result")
    private Results result;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Records")
    private java.util.List<E> records;
    @JsonProperty("Record")
    private E record;

    public enum Results {OK, ERROR}


    public JTableResponse(Results result, List<E> records) {
        this.result = result;
        this.records = records;
    }


    public JTableResponse(Results result) {
        this.result = result;
    }


    public Results getResult() {

        return result;
    }

    public void setResult(Results result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void addMessage(String message) {
        if (this.message != null && this.message.length() > 0) {
            this.message += "<br>" + message;
        } else {
            setMessage(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<E> getRecords() {
        return records;
    }

    public void setRecords(List<E> records) {
        this.records = records;
    }

    public E getRecord() {
        return record;
    }

    public void setRecord(E record) {
        this.record = record;
    }
}
