package iblapps.findcau;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaCaus extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String urlMapaAgrupaments = "https://www.google.com/maps/d/viewer?ll=41.76721499999998%2C1.9390869999999722&spn=2.048536%2C2.741089&hl=ca&t=h&msa=0&z=8&source=embed&ie=UTF8&mid=1dFgHrsRNw_8Iju0r8ZPnHCJNskU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_caus);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton btnZoomIn = (FloatingActionButton) findViewById(R.id.btnZoomIn);
        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mMap.moveCamera(CameraUpdateFactory.zoomIn());
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        FloatingActionButton btnZoomOut = (FloatingActionButton) findViewById(R.id.btnZoomOut);
        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mMap.moveCamera(CameraUpdateFactory.zoomOut());
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double lat = 42.033830;
        double lng =  1.883013;
        LatLng cau = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions()
                .position(cau).title("AEiG Sebastià Montraveta")
                .snippet("Adreça: Plaça de l'Esglesia s/n"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(cau));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cau,mMap.getMaxZoomLevel()));
        /*
        //EXAMPLE ******************************************************
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }
}
