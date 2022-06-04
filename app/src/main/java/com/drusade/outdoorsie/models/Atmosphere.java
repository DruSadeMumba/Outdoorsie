
package com.drusade.outdoorsie.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Atmosphere {

    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("visibility")
    @Expose
    private Double visibility;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("rising")
    @Expose
    private Integer rising;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Atmosphere() {
    }

    /**
     * 
     * @param rising
     * @param visibility
     * @param humidity
     * @param pressure
     */
    public Atmosphere(Integer humidity, Double visibility, Integer pressure, Integer rising) {
        super();
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
        this.rising = rising;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getRising() {
        return rising;
    }

    public void setRising(Integer rising) {
        this.rising = rising;
    }

}
