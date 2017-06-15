package com.badeeb.waritex.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ahmed on 6/7/2017.
 */

public class Promotion {

    // Class Attributes
    @Expose(serialize = false, deserialize = true)
    @SerializedName("id")
    private int id;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("title")
    private String title;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("description")
    private String description;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("due_date")
    private String dueDate;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("main_photo")
    private String mainPhoto;

    /*
    @Expose(serialize = false, deserialize = true)
    @SerializedName("photos")
    private List<Photo> photos;
    */

    public static final String DATE_FORMAT_DISPLAY_PATTERN = "dd-MMM-yyyy";
    public static final String DATE_FORMAT_WRITE_PATTERN = "dd-MMM-yyyy";
    public static final String PROMOTION_TAG_LOG = "Promotion_Object";

    // Setters and Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }


    public String getDueDateFormated(){
        /*
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DISPLAY_PATTERN);
        return dateFormat.format(getDueDate());
        */
        return "";
    }

    public void setDueDateFormated(String dueDateText){
        /*
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_WRITE_PATTERN);
        try {
            setDueDate(dateFormat.parse(dueDateText));
        } catch (ParseException e) {
            Log.d(PROMOTION_TAG_LOG, dueDateText+" - "+ e.getMessage());
        }
        */
    }


}
