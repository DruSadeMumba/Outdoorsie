
package com.drusade.outdoorsie.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("chill")
    @Expose
    private Integer chill;
    @SerializedName("direction")
    @Expose
    private Integer direction;
    @SerializedName("speed")
    @Expose
    private Double speed;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Wind() {
    }

    /**
     * 
     * @param chill
     * @param speed
     * @param direction
     */
    public Wind(Integer chill, Integer direction, Double speed) {
        super();
        this.chill = chill;
        this.direction = direction;
        this.speed = speed;
    }

    public Integer getChill() {
        return chill;
    }

    public void setChill(Integer chill) {
        this.chill = chill;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

}
