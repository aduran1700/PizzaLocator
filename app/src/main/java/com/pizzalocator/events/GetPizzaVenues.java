package com.pizzalocator.events;


import lombok.Data;

/**
 * Event to get pizza venues
 */
@Data
public class GetPizzaVenues {
    private double latitude;
    private double longitude;

    /**
     * Creates a GetPizzaVenues Event
     * @param latitude the latitude of the location you want to search from
     * @param longitude the longitude of the location you want to search from
     */
    public GetPizzaVenues(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
