import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AFconst {

    private String stareInitiala;
    private final Set<String> stariFinale;
    private final List<Tranzitie> tranzitii;
    Set<String> digits = new HashSet<>();
    Set<String> digits_1_9 = new HashSet<>();

    public AFconst() {
        Set<String> multimeStari = new HashSet<>();
        Set<String> alfabetIntrare = new HashSet<>();
        stariFinale = new HashSet<>();
        tranzitii = new ArrayList<>();
        Collections.addAll(digits, "0","1","2","3","4","5","6","7","8","9");
        Collections.addAll(digits_1_9, "1","2","3","4","5","6","7","8","9");


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/inputFiles/AFconst"))) {
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
        if(Objects.equals(sequence, "0")){
            return true;
        }
        for (char character : sequence.toCharArray()) {
            for (Tranzitie tranzitie : tranzitii) {
                if (tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals("-")) {
                    if (character == '-') {
                        stareFinala = tranzitie.getStareFinala();
                        break;
                    }
                }
                if (tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals("digit_1_9")) {
                    if (digits_1_9.contains(String.valueOf(character))) {
                        stareFinala = tranzitie.getStareFinala();
                        break;
                    }
                }
                if (tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals("digit")) {
                    if(digits.contains(String.valueOf(character))) {
                        stareFinala = tranzitie.getStareFinala();
                        break;
                    }
                }
            }

            if (stareFinala.equals("")) {
                return false;
            }
            stareInitiala = stareFinala;
            stareFinala = "";
        }
        return stariFinale.contains(stareInitiala);
    }

}
