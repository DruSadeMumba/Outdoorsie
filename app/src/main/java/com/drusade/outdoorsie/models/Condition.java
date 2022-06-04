
package com.drusade.outdoorsie.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Condition {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("temperature")
    @Expose
    private Integer temperature;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Condition() {
    }

    /**
     * 
     * @param code
     * @param temperature
     * @param text
     */
    public Condition(Integer code, String text, Integer temperature) {
        super();
        this.code = code;
        this.text = text;
        this.temperature = temperature;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

}
