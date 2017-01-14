package iblapps.findcau;

/**
 * Created by ibern on 7/1/2017.
 */

public class Direccio {
    public String ciutat;
    public String carrer;
    public int codiPostal;
    public  Coordenades coordenades;

    public Direccio()
    {
        this.ciutat = "";
        this.carrer = "";
        this.codiPostal = 0;
        this.coordenades = new Coordenades();
    }
}
