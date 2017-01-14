package iblapps.findcau;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ibern on 7/1/2017.
 */

public class Allotjament {
    public String nom;
    public Direccio direccio;
    public String email;
    public int maximPlaces;
    public String urlImage;
    public String llocWeb;

    //public Marker marker;

    public Allotjament()
    {
        this.direccio = new Direccio();
        //this.marker = new MarkerOptions().position(new LatLng(this.direccio.coordenades.x,this.direccio.coordenades.y)));
    }

}
