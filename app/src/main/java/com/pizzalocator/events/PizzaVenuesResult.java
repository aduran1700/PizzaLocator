package com.pizzalocator.events;


import com.pizzalocator.models.Venue;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Event that holds a List of venues
 */
@Data
public class PizzaVenuesResult {

    private List<Venue> venueList;

    /**
     * Creates a PizzaVenue Event
     * @param venueList A List of venues
     */
    public PizzaVenuesResult(List<Venue> venueList) {
        this.venueList = venueList;
    }


}
