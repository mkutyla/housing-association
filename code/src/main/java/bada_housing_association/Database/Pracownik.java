package bada_housing_association.Database;

public class Pracownik {
    private int nr_pracownika;
    private String imie;
    private String nazwisko;
    private String data_urodzenia;
    private String pesel;
    private char plec;
    private String data_zatrudnienia;
    private String nr_konta;
    private int wynagrodzenie;
    private String email;
    private String nr_telefonu;
    private int nr_spoldzielni;
    private int nr_adresu;
    private int nr_stanowiska;


    public Pracownik() {

    }

    public Pracownik(int nr_pracownika,
                     String imie,
                     String nazwisko,
                     String data_urodzenia,
                     String pesel,
                     char plec,
                     String data_zatrudnienia,
                     String nr_konta,
                     int wynagrodzenie,
                     String email,
                     String nr_telefonu,
                     int nr_spoldzielni,
                     int nr_adresu,
                     int nr_stanowiska) {
        super();
        this.nr_pracownika = nr_pracownika;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.data_urodzenia = data_urodzenia;
        this.pesel = pesel;
        this.plec = plec;
        this.data_zatrudnienia = data_zatrudnienia;
        this.nr_konta = nr_konta;
        this.wynagrodzenie = wynagrodzenie;
        this.email = email;
        this.nr_telefonu = nr_telefonu;
        this.nr_spoldzielni = nr_spoldzielni;
        this.nr_adresu = nr_adresu;
        this.nr_stanowiska = nr_stanowiska;
    }

    public char getPlec() {
        return plec;
    }

    public void setPlec(char plec) {
        this.plec = plec;
    }

    public int getNr_adresu() {
        return nr_adresu;
    }

    public int getNr_pracownika() {
        return nr_pracownika;
    }

    public void setNr_pracownika(int nr_pracownika) {
        this.nr_pracownika = nr_pracownika;
    }

    public String getImie() {
        return imie;
    }

    public int getNr_spoldzielni() {
        return nr_spoldzielni;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setData_urodzenia(String data_urodzenia) {
        this.data_urodzenia = data_urodzenia;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setData_zatrudnienia(String data_zatrudnienia) {
        this.data_zatrudnienia = data_zatrudnienia;
    }

    public void setNr_konta(String nr_konta) {
        this.nr_konta = nr_konta;
    }

    public void setWynagrodzenie(int wynagrodzenie) {
        this.wynagrodzenie = wynagrodzenie;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getData_urodzenia() {
        return data_urodzenia;
    }

    public String getPesel() {
        return pesel;
    }

    public String getData_zatrudnienia() {
        return data_zatrudnienia;
    }

    public String getNr_konta() {
        return nr_konta;
    }

    public int getWynagrodzenie() {
        return wynagrodzenie;
    }

    public String getEmail() {
        return email;
    }

    public String getNr_telefonu() {
        return nr_telefonu;
    }

    public int getNr_stanowiska() {
        return nr_stanowiska;
    }

    public void setNr_telefonu(String nr_telefonu) {
        this.nr_telefonu = nr_telefonu;
    }

    public void setNr_spoldzielni(int nr_spoldzielni) {
        this.nr_spoldzielni = nr_spoldzielni;
    }

    public void setNr_adresu(int nr_adresu) {
        this.nr_adresu = nr_adresu;
    }

    public void setNr_stanowiska(int nr_stanowiska) {
        this.nr_stanowiska = nr_stanowiska;
    }

    @Override
    public String toString() {
        return "Pracownik{" +
                "nr_pracownika=" + nr_pracownika +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", data_urodzenia='" + data_urodzenia + '\'' +
                ", pesel='" + pesel + '\'' +
                ", data_zatrudnienia='" + data_zatrudnienia + '\'' +
                ", nr_konta='" + nr_konta + '\'' +
                ", wynagrodzenie=" + wynagrodzenie +
                ", email='" + email + '\'' +
                ", nr_telefonu='" + nr_telefonu + '\'' +
                ", nr_spoldzielni=" + nr_spoldzielni +
                ", nr_adresu=" + nr_adresu +
                ", nr_stanowiska=" + nr_stanowiska +
                '}';
    }
}
