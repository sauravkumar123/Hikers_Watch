package com.example.hikers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
        TextView lat;
        TextView lon;
        TextView accuracy;
        TextView addresssss;
        TextView alti;
        TextView date;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1)
        {
            if(grantResults.length>1&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }
    public void updatelocationinfo(Location location)
    {
        lat=(TextView)findViewById(R.id.textlat);
        lon=(TextView)findViewById(R.id.longit);
        accuracy=(TextView)findViewById(R.id.acc);
        addresssss=(TextView)findViewById(R.id.add);
        alti=(TextView)findViewById(R.id.alti);
        lat.setText(String.valueOf(location.getLatitude()) );
        lon.setText(String.valueOf(location.getLongitude()) );
        accuracy.setText(String.valueOf(location.getAccuracy()) );
        alti.setText(String.valueOf(location.getAltitude()));
        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            String add="Could Not find you Address,Sorryy!!!";
            List<Address> address=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if(address!=null&&address.size()>0)

            {
                add="";

                if(address.get(0).getSubThoroughfare()!=null)
                {
                    add =add+address.get(0).getSubThoroughfare()+"\n";
                }
                if(address.get(0).getThoroughfare()!=null)
                {
                    add=add+address.get(0).getThoroughfare()+"\n";
                }
                if(address.get(0).getLocality()!=null)
                {
                    add=add+address.get(0).getLocality()+"\n";
                }

                if(address.get(0).getPostalCode()!=null)
                {
                    add=add+address.get(0).getPostalCode()+"\n";

                }
                if (address.get(0).getCountryName()!=null)
                {
                    add=add+address.get(0).getCountryName();
                }

            }
            addresssss.setText(String.valueOf(add));



        }       catch(IOException e)
            {
        e.printStackTrace();
                }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date=(TextView)findViewById(R.id.date);
        Calendar calendar=Calendar.getInstance();
        String currentdate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentdate);
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updatelocationinfo(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastlocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastlocation!=null)
            {
                updatelocationinfo(lastlocation);
            }

        }

    }
}
