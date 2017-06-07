package com.badeeb.waritex.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ahmed on 6/7/2017.
 */

public class Promotion {
    private String title;
    private String description;
    private Date dueDate;
    private String mainPhoto;

    public static final String DATE_FORMAT_DISPLAY_PATTERN = "dd-MMM-yyyy";
    public static final String DATE_FORMAT_WRITE_PATTERN = "dd-MMM-yyyy";
    public static final String PROMOTION_TAG_LOG = "Promotion_Object";

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getMainPhoto() {
        return mainPhoto;
    }
    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getDueDateFormated(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DISPLAY_PATTERN);
        return dateFormat.format(getDueDate());
    }
    public void setDueDateFormated(String dueDateText){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_WRITE_PATTERN);
        try {
            setDueDate(dateFormat.parse(dueDateText));
        } catch (ParseException e) {
            Log.d(PROMOTION_TAG_LOG, dueDateText+" - "+ e.getMessage());
        }
    }


}
