package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amr Alghawy on 6/13/2017.
 */

public class JsonData<T> {

    @Expose(serialize = false, deserialize = true)
    @SerializedName("data")
    private T data;
}
