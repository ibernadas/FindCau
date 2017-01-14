package iblapps.findcau;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MapaCaus extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener  {

    private GoogleMap mMap;

    private String urlMapaAgrupamentsMEG = "https://www.google.com/maps/d/viewer?ll=41.76721499999998%2C1.9390869999999722&spn=2.048536%2C2.741089&hl=ca&t=h&msa=0&z=8&source=embed&ie=UTF8&mid=1dFgHrsRNw_8Iju0r8ZPnHCJNskU";
    private String urlMapaAgrupamentsEC = "http://www.escoltes.org/on-som";
    private String urlAccioEscola = "http://www.accioescolta.cat/agrupaments/";

    private String urlMapaEsplac = "http://www.esplac.cat/cercador-desplais/";
    private String urlMapaEsplacMarkers = "http://www.esplac.cat/wp-content/themes/candidate/mapa_markers.php";

    private String urlMapaRefugisFEEC = "https://www.feec.cat/refugis/";

    public List<Allotjament> allotjaments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_caus);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

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

        //readLocationsXML();
        //showPlaces();
    }

    private void showPlaces() {
        //for (int i = 0; i < allotjaments.size(); i++)
        for (Allotjament currentAllotjament: allotjaments)
        {
            try
            {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(currentAllotjament.direccio.coordenades.x,currentAllotjament.direccio.coordenades.y))
                        .title(currentAllotjament.nom));
                //.snippet("Adreça: Plaça de l'Esglesia s/n"));
            }catch (Exception e)
            {
                Toast toast = new Toast(this);
                toast.setText("Hi ha hagut algun problema");

            }

        }
    }


    public void readLocationsXML() {
        allotjaments = new ArrayList<Allotjament>();

        try {
            //Omplir llista de caus de MEG
            InputStream is = this.getResources().openRawResource(R.raw.locations_meg);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            String nodeName = doc.getDocumentElement().getNodeName();

            NodeList nList = doc.getElementsByTagName("Placemark");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Cau cau = new Cau();
                Direccio direccio = new Direccio();

                Node nNode = nList.item(temp);
                NodeList info = nNode.getChildNodes();
                for (int i = 0; i < info.getLength(); i++) {
                    Node var = info.item(i);
                    if (var.getNodeName().equals("name")) {
                        cau.nom = var.getTextContent();
                    }
                    if (var.getNodeName().equals("description")) {
                        String description = var.getTextContent();

                        String[] elements = description.split("<br>");
                        int offset = 0;

                        if (elements[0].split("img src=").length > 1)
                        {
                            cau.urlImage = elements[0].split("img src=")[1].split("\"")[1];
                            offset = 2;
                        }

                        direccio.carrer = elements[offset];
                        direccio.ciutat = elements[offset+1];
                        cau.demarcacio = elements[offset+2];
                        cau.email = elements[offset+3];
                        if(offset+4<elements.length)
                        {
                            cau.llocWeb = elements[offset+4];
                        }

                    }
                    if (var.getNodeName().equals("Point")) {
                        String strCord = var.getChildNodes().item(1).getTextContent();
                        String[] coordenades = strCord.split(",");
                        direccio.coordenades.y = Double.parseDouble(coordenades[0]);
                        direccio.coordenades.x = Double.parseDouble(coordenades[1]);

                    }
                    cau.direccio = direccio;
                }
                allotjaments.add(cau);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        try {
            InputStream is = this.getResources().openRawResource(R.raw.locations_ec);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            String nodeName = doc.getDocumentElement().getNodeName();

            NodeList nList = doc.getElementsByTagName("element");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Cau cau = new Cau();
                Coordenades coordenades = new Coordenades();

                for (int temp2 = 0; temp2< nList.item(temp).getChildNodes().getLength(); temp2++)
                {
                    if (nList.item(temp).getChildNodes().item(temp2).getNodeName().equals("latitude"))
                        coordenades.x = Double.parseDouble(nList.item(temp).getChildNodes().item(temp2).getTextContent());
                    else if (nList.item(temp).getChildNodes().item(temp2).getNodeName().equals("longitude"))
                        coordenades.y = Double.parseDouble(nList.item(temp).getChildNodes().item(temp2).getTextContent());
                    else if (nList.item(temp).getChildNodes().item(temp2).getNodeName().equals("text"))
                    {
                        String text = nList.item(temp).getChildNodes().item(temp2).getTextContent();
                    }
                }


            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
                .snippet("Adreça: Plaça de l'Esglesia s/n")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(cau));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cau,mMap.getMaxZoomLevel()));

        readLocationsXML();
        showPlaces();

        /*
        //EXAMPLE ******************************************************
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mapa_caus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
