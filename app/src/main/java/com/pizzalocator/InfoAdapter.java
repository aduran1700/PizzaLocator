package com.pizzalocator;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

class InfoAdapter implements GoogleMap.InfoWindowAdapter {
    private View info=null;
    private LayoutInflater inflater=null;

    InfoAdapter(LayoutInflater inflater) {
        this.inflater=inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        if (info == null) {
            info=inflater.inflate(R.layout.info, null);
        }

        TextView tv=(TextView)info.findViewById(R.id.title);

        tv.setText(marker.getTitle());
        tv=(TextView)info.findViewById(R.id.snippet);
        tv.setText(marker.getSnippet());

        return info;
    }
}