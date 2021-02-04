package com.example.vehicle_try;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity {

    public String UrlFetchData="http://192.168.1.8/VehicleBook/ListData_new.php";
    String[] id;
    String[] servicecentername;
    String[] phoneno;
    String[] address;
    String[] area;
    ListView listView ;
    RequestQueue queue;
    Context context;
    JsonArray array;
    public int locationStatus = 0;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.lst);
        context=this;

        queue = Volley.newRequestQueue(this);


        StringRequest makeRequest = new StringRequest(Request.Method.POST, UrlFetchData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {
                        System.out.println(res);
                        Gson gson = new Gson();
                        if (res.contains("id") && res.contains("servicecentername") && res.contains("address") ) {

                            array = gson.fromJson(res, JsonArray.class);
                            id = new String[array.size()];
                            servicecentername = new String[array.size()];
                            phoneno = new String [array.size()];
                            address = new String [array.size()];
                            area = new String [array.size()];
                            for (int i = 0; i < array.size(); i++) {
                                System.out.println("in loop");
                                JsonObject jobj = array.get(i).getAsJsonObject();

                                id[i] = jobj.get("id").getAsString();
                                servicecentername[i] = jobj.get("servicecentername").getAsString();
                                phoneno[i]=jobj.get("phoneno").getAsString();
                                address[i]=jobj.get("address").getAsString();
                                area[i]=jobj.get("area").getAsString();

                            }
                            MyAdapter myAdapter = new MyAdapter(context, id, servicecentername, phoneno,address,area);
                            listView.setAdapter(myAdapter);

                        }
                    }


                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Once the request is performed, failed code over here is executed
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();

                            return params;
                        }
                    };
                            queue.add(makeRequest);
//                            for(int i=0;i<array.size();i++){
//                                System.out.println(name[i]+" "+add[i]+" "+phn[i]);
//                            }





    }
    public void networkCall(String hi){
        System.out.println(hi);

    }

    LocationManager locationManager;
    public void getMap(String place){
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.i("LOCATION", "Latitude:" + latitude + ", Longitude:" + longitude);
                    Toast.makeText(MainActivity.this, "Latitude:" + latitude + ", Longitude:" + longitude, Toast.LENGTH_SHORT).show();
                    //getWeaterData(longitude, latitude);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.i("LOCATION", "disable");
                    Toast.makeText(MainActivity.this, "Please enable your Location Service.", Toast.LENGTH_LONG).show();
                    ;
                    locationStatus = -1;
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.i("LOCATION", "enable");
                    locationStatus = 1;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.i("LOCATION", "status" + status);
                }

            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude +"?z=10&q=nursery");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}