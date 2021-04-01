package com.bik.telefood.ui.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.ConfirmDialog;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

@Keep
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConfirmDialog.OnPositiveButtonListener {

    private static final int FINE_LOCATION_PERMISSION_REQUEST = 1;
    private GoogleMap mMap;
    private Double latitude;
    private Double longitude;
    private FragmentMapsBinding fragmentMapsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentMapsBinding = FragmentMapsBinding.inflate(getLayoutInflater());
        setContentView(fragmentMapsBinding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, FINE_LOCATION_PERMISSION_REQUEST);
        } else {
            mMap.setMyLocationEnabled(true);

            mMap.setOnMapClickListener(latLng -> {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            });

            fragmentMapsBinding.btnSelectLocation.setOnClickListener(view -> {
                if (latitude != null && longitude != null) {
                    Intent data = new Intent();
                    data.putExtra(AppConstant.LAT, latitude);
                    data.putExtra(AppConstant.LNG, longitude);
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    new MessageDialog(this, getString(R.string.title_error_message), getString(R.string.error_msg_missing_placeholder));
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                new ConfirmDialog(this, getString(R.string.permission_request), getString(R.string.alert_msg_map_permission), this);
            } else {
                onMapReady(mMap);
            }
        }
    }


    @Override
    public void onClick() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, FINE_LOCATION_PERMISSION_REQUEST);
    }
}