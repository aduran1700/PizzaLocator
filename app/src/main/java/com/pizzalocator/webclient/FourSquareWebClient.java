package com.pizzalocator.webclient;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Creates a AsyncHttpClient to handle interactions with
 * Foursquare's Api
 */
public class FourSquareWebClient {

    private static final String BASE_URL = "https://api.foursquare.com/v2/";
    private static final String CLIENT_ID = "MSB3H0C14BLRSSAAYGELVLAAWVKWUL5DXYVMWF2UJPUVZOMC";
    private static final String CLIENT_SECRET = "ZSDRDRBEZHO4DUH5TWS2DQMOUP1MMBBMEZJBNDZZVXTUFZX1";

    private static AsyncHttpClient client = new AsyncHttpClient();


    /**
     * Performs a Get request
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);

        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
