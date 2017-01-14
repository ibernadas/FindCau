package iblapps.findcau;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ibern on 7/1/2017.
 */

public class Moviment {
    public String nom;
    public String llocWeb;
    public ArrayList<Demarcacio> demarcacions;

    public Moviment()
    {
        this.demarcacions = new ArrayList<Demarcacio>();
    }
}
