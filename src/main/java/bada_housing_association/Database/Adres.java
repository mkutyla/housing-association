package bada_housing_association.Database;

public class Adres {
    private int nr_adresu;
    private String ulica;
    private String nr_budynku;
    private String nr_lokalu;
    private String miasto;

    public Adres() {

    }

    public Adres(int nr_adresu, String ulica, String nr_budynku, String nr_lokalu, String miasto) {
        super();
        this.nr_adresu = nr_adresu;
        this.ulica = ulica;
        this.nr_budynku = nr_budynku;
        this.nr_lokalu = nr_lokalu;
        this.miasto = miasto;
    }


    public int getNr_adresu() {
        return nr_adresu;
    }

    public void setNr_adresu(int nr_adresu) {
        this.nr_adresu = nr_adresu;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getNr_budynku() {
        return nr_budynku;
    }

    public void setNr_budynku(String nr_budynku) {
        this.nr_budynku = nr_budynku;
    }

    public String getNr_lokalu() {
        return nr_lokalu;
    }

    public void setNr_lokalu(String nr_lokalu) {
        this.nr_lokalu = nr_lokalu;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ulica).append(", ").append(nr_budynku);
        if (nr_budynku == null) sb.append("/").append(nr_lokalu);
        sb.append(", ").append(miasto);
        return sb.toString();
    }
}
