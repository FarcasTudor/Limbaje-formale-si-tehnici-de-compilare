import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutomatFinit {
    private Set<String> stari;
    private Set<String> alfabetIntrare;
    private List<Tranzitie> tranzitii;
    private String stareInitiala;
    private Set<String> stariFinale;

    public AutomatFinit(Set<String> stari, Set<String> alfabetIntrare, List<Tranzitie> tranzitii, String stareInitiala, Set<String> stariFinale) {
        this.stari = stari;
        this.alfabetIntrare = alfabetIntrare;
        this.tranzitii = tranzitii;
        this.stareInitiala = stareInitiala;
        this.stariFinale = stariFinale;
    }

    public void printMultimeStari() {
        System.out.println("Multimea starilor:");
        for (String stare : stari) {
            System.out.print(stare + " ");
        }
        System.out.println();
    }

    public void printAlfabetIntrare() {
        System.out.println("Alfabetul de intrare:");
        for (String simbol : alfabetIntrare) {
            System.out.print(simbol + " ");
        }
        System.out.println();
    }

    public void printTranzitii() {
        System.out.println("Tranzitiile:");
        for (Tranzitie tranzitie : tranzitii) {
            System.out.println(tranzitie);
        }
    }

    public void printStariFinale() {
        System.out.println("Starile finale:");
        for (String stare : stariFinale) {
            System.out.print(stare + " ");
        }
        System.out.println();
    }

    private String gasesteUrmatoareaStare(String stareCurenta, String valoare) {
        for (Tranzitie tranzitie : tranzitii) {
            if (tranzitie.getStareInitiala().equals(stareCurenta) && tranzitie.getValoare().equals(valoare)) {
                return tranzitie.getStareFinala();
            }
        }
        return "";
    }
    public boolean verificaSecventa(String secventa) {
        String stareCurenta = this.stareInitiala;
        String stareFinala = ""; // bab

        for(int i = 0; i < secventa.length(); i++) {
            String valoare = secventa.substring(i, i + 1);
            String nextCharacter = gasesteUrmatoareaStare(stareCurenta, valoare);
            if(nextCharacter.isEmpty()) {
                stareFinala = "";
                break;
            }

            if(stariFinale.contains(nextCharacter) && i == secventa.length() - 1) {
                stareFinala = nextCharacter;
                break;
            }
            stareCurenta = nextCharacter;
            stareFinala = nextCharacter;
        }
        return stariFinale.contains(stareFinala);
    }

    public String celMaiLungPrefix(String sequence) {
        String stareCurenta = this.stareInitiala;
        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < sequence.length(); i++) {
            String character = sequence.substring(i, i + 1);
            String nextCharacter = gasesteUrmatoareaStare(stareCurenta, character);

            if (nextCharacter.isEmpty()) {
                break;
            }

            prefix.append(character);
            stareCurenta = nextCharacter;
        }
        return stariFinale.contains(stareCurenta) ? prefix.toString() : "";
    }

    public boolean isDeterminist() {
        Set<String> stareSimboluri = new HashSet<>();

        for (Tranzitie tranzitie : tranzitii) {
            String key = tranzitie.getStareInitiala() + " " + tranzitie.getValoare();
            if (stareSimboluri.contains(key)) {
                return false;
            }
            stareSimboluri.add(key);
        }
        return true;
    }
}
