package com.quicktour.service;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class SqlDatePropertyEditor extends PropertyEditorSupport {
    public static final String DEFAULT_BATCH_PATTERN = "yyyy-MM-dd";
    private final SimpleDateFormat sdf;

    public SqlDatePropertyEditor() {
        this.sdf = new SimpleDateFormat(SqlDatePropertyEditor.DEFAULT_BATCH_PATTERN);
    }

    public SqlDatePropertyEditor(String pattern) {
        this.sdf = new SimpleDateFormat(pattern);
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? this.sdf.format(value) : "");
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try {
            if (text != null && text.length() > 0) {
                setValue(new Date(this.sdf.parse(text).getTime()));
            }
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
        }
    }
}
