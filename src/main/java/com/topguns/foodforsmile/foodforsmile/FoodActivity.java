package com.topguns.foodforsmile.foodforsmile;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class FoodActivity extends ListActivity {

    LocationManager locationManager;
    private static final String API_KEY = "add your api key here";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //get current location of user
        String latitudeLongitude = new LocationHelper().getLocation(this, locationManager);
        if (null != latitudeLongitude) {
            String[] coordinates = latitudeLongitude.split(";");

            double latitude = Double.parseDouble(coordinates[0]);
            double longitude = Double.parseDouble(coordinates[1]);

            new GetPlaces(this, getListView(), latitude, longitude).execute();

        }
    }

    class GetPlaces extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private Context context;
        private String[] placeName;
        private String[] imageUrl;
        private ListView listView;
        private double latitude;
        private double longitude;

        public GetPlaces(Context context, ListView listView, double latitude, double longitude) {
            this.context = context;
            this.listView = listView;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();

            listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, placeName));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setCancelable(true);
            dialog.setMessage("Loading..");
            dialog.isIndeterminate();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            PlacesService service = new PlacesService(API_KEY);
            List<Place> findPlaces = service.findPlaces(latitude, longitude, "hospital");
            // atm for ATM

            placeName = new String[findPlaces.size()];
            imageUrl = new String[findPlaces.size()];

            for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                placeDetail.getIcon();

                System.out.println(placeDetail.getName());
                placeName[i] = placeDetail.getName();

                imageUrl[i] = placeDetail.getIcon();

            }
            return null;
        }

    }

}
