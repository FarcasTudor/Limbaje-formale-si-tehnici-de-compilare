import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AFid {
    private Set<String> multimeStari;
    private Set<String> alfabetIntrare;

    private String stareInitiala;
    private Set<String> stariFinale;
    private List<Tranzitie> tranzitii;
    private Set<String> letters = new HashSet<>();
    private Set<String> digits = new HashSet<>();

    public AFid() {
        multimeStari = new HashSet<>();
        alfabetIntrare = new HashSet<>();
        stariFinale = new HashSet<>();
        tranzitii = new ArrayList<>();
        Collections.addAll(letters, "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","_");
        Collections.addAll(digits, "0","1","2","3","4","5","6","7","8","9");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/inputFiles/AFid"))) {
            String[] data = bufferedReader.readLine().split(" ");
            multimeStari.addAll(List.of(data));
            stareInitiala = bufferedReader.readLine();
            data = bufferedReader.readLine().split(" ");
            stariFinale.addAll(List.of(data));
            int noOfTransitions = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < noOfTransitions; i++) {
                data = bufferedReader.readLine().split(" ");
                tranzitii.add(new Tranzitie(data[0], data[2], data[1]));
                alfabetIntrare.add(data[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifySequence(String sequence) {
        String stareInitiala = this.stareInitiala;
        String stareFinala = "";

        if (sequence.length() > 250)
            return false;

        for (char caracter : sequence.toCharArray()) {
            for (Tranzitie tranzitie : tranzitii) {
                if (tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals("letter")) {
                    if (letters.contains(String.valueOf(caracter))) {
                        stareFinala = tranzitie.getStareFinala();
                        break;
                    }
                }
                if (tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals("digit")) {
                    if (digits.contains(String.valueOf(caracter))) {
                        stareFinala = tranzitie.getStareFinala();
                        break;
                    }
                }
            }
            if (stareFinala.equals(""))
                return false;
            stareInitiala = stareFinala;
            stareFinala = "";
        }
        return stariFinale.contains(stareInitiala);
    }



}
