package com.pizzalocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.pizzalocator.models.Venue;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VenueDetailActivity extends AppCompatActivity {


    @InjectView(R.id.address)
    TextView address;

    @InjectView(R.id.phone)
    TextView phone;

    @InjectView(R.id.menu)
    WebView menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_detail);
        ButterKnife.inject(this);

        Venue venue = (Venue) getIntent().getSerializableExtra("Venue");

        getSupportActionBar().setTitle(venue.getName());

        if(venue.getFormattedPhone().equals(""))
            phone.setVisibility(View.GONE);
        else
            phone.setText(venue.getFormattedPhone());

        if(venue.getAddress().equals(""))
            address.setVisibility(View.GONE);
        else
            address.setText(venue.getAddress() + "\n" + venue.getCity() + ", " + venue.getState());

        if(venue.isHasMenu()) {
            menu.loadUrl(venue.getMobileUrl());
            menu.setVisibility(View.VISIBLE);
        } else
            menu.setVisibility(View.GONE);
    }
}
