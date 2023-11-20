public class Tranzitie {
    private final String stareInitiala;
    private String stareFinala;
    private final String valoare;

    public Tranzitie(String stareInitiala, String stareFinala, String valoare) {
        this.stareInitiala = stareInitiala;
        this.stareFinala = stareFinala;
        this.valoare = valoare;
    }

    public String getStareInitiala() {
        return stareInitiala;
    }

    public String getStareFinala() {
        return stareFinala;
    }

    public void setStareFinala(String stareFinala) {
        this.stareFinala = stareFinala;
    }

    public String getValoare() {
        return valoare;
    }

    @Override
    public String toString() {
        return this.stareInitiala + " -> " + this.valoare + " -> " + this.stareFinala;
    }
}
