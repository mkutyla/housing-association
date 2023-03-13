package bada_housing_association.Database;

public class Spoldzielnia {
    private int nr_spoldzielni;
    private String nazwa;
    private String data_zalozenia;
    private int nr_adresu;

    public Spoldzielnia(){

    }
    public Spoldzielnia(int nr_spoldzielni, String nazwa, String data_zalozenia, int nr_adresu){
        super();
        this.nr_spoldzielni = nr_spoldzielni;
        this.nazwa = nazwa;
        this.data_zalozenia = data_zalozenia;
        this.nr_adresu = nr_adresu;
    }

    public int getNr_spoldzielni() {
        return nr_spoldzielni;
    }

    public void setNr_spoldzielni(int nr_spoldzielni) {
        this.nr_spoldzielni = nr_spoldzielni;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getData_zalozenia() {
        return data_zalozenia;
    }

    public void setData_zalozenia(String data_zalozenia) {
        this.data_zalozenia = data_zalozenia;
    }

    public int getNr_adresu() {
        return nr_adresu;
    }

    public void setNr_adresu(int nr_adresu) {
        this.nr_adresu = nr_adresu;
    }

    @Override
    public String toString() {
        return "Spoldzielnie{" +
                "nr_spoldzielni=" + nr_spoldzielni +
                ", nazwa='" + nazwa + '\'' +
                ", data_zalozenia='" + data_zalozenia + '\'' +
                ", nr_adresu=" + nr_adresu +
                '}';
    }
}
