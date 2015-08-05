package com.pizzalocator.subscribers;



import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pizzalocator.PLEventBus;
import com.pizzalocator.R;
import com.pizzalocator.base.BaseSubscriber;
import com.pizzalocator.events.GetPizzaVenues;
import com.pizzalocator.events.PizzaVenuesResult;
import com.pizzalocator.models.Venue;
import com.pizzalocator.webclient.FourSquareWebClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Subscribe to GetPizzaVenues queries Foursquare for pizza venues
 * and send PizzaVenuesResult event to the bus
 */
public class PizzaVenuesSubscriber extends BaseSubscriber {


    public void onEvent(final GetPizzaVenues event) {
        final ArrayList<Venue> venueList = new ArrayList<>();

        RequestParams params = new RequestParams();
        params.add("query", "Pizza");
        params.add("ll", event.getLatitude() +", " + event.getLongitude());
        params.add("v", "20150628");

        FourSquareWebClient.get("venues/search", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray venues = response.getJSONObject("response").getJSONArray("venues");

                    for(int i = 0; i < venues.length(); i++) {
                        Venue venue = new Venue();
                        JSONObject jsonObject = venues.getJSONObject(i);
                        venue.setName(jsonObject.getString("name"));

                        if(jsonObject.has("hasMenu")) {
                            venue.setHasMenu(jsonObject.getBoolean("hasMenu"));

                            if (venue.isHasMenu()) {
                                JSONObject menu = jsonObject.getJSONObject("menu");
                                venue.setMobileUrl(menu.getString("mobileUrl"));
                            }
                        }

                        if(jsonObject.has("location")) {
                            JSONObject location = jsonObject.getJSONObject("location");

                            venue.setLatitude(location.has("lat") ? location.getDouble("lat") : 0);
                            venue.setLongitude(location.has("lng") ? location.getDouble("lng") : 0);
                            venue.setAddress(location.has("address") ? location.getString("address") : "");
                            venue.setCity(location.has("city") ? location.getString("city") : "");
                            venue.setState(location.has("state") ? location.getString("state") : "");
                        }

                        if(jsonObject.has("contact")) {
                            JSONObject contact = jsonObject.getJSONObject("contact");

                            venue.setFormattedPhone(contact.has("formattedPhone") ? contact.getString("formattedPhone") : "");
                        }

                        venueList.add(venue);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                PLEventBus.postEvent(new PizzaVenuesResult(venueList));

            }
        });


    }

}
