
package com.drusade.outdoorsie.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class YahooWeatherLocationSearchResponse {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("current_observation")
    @Expose
    private CurrentObservation currentObservation;
    @SerializedName("forecasts")
    @Expose
    private List<Forecast> forecasts = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public YahooWeatherLocationSearchResponse() {
    }

    /**
     * 
     * @param currentObservation
     * @param location
     * @param forecasts
     */
    public YahooWeatherLocationSearchResponse(Location location, CurrentObservation currentObservation, List<Forecast> forecasts) {
        super();
        this.location = location;
        this.currentObservation = currentObservation;
        this.forecasts = forecasts;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CurrentObservation getCurrentObservation() {
        return currentObservation;
    }

    public void setCurrentObservation(CurrentObservation currentObservation) {
        this.currentObservation = currentObservation;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

}
