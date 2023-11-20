import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.AuthProvider;
import java.util.*;
import java.util.stream.Collectors;

public class AFbinar {

    private Set<String> multimeStari = new HashSet<>();
    private Set<String> alfabetIntrare = new HashSet<>();
    private List<Tranzitie> tranzitii = new ArrayList<>();
    private String stareInitiala;
    private Set<String> stariFinale = new HashSet<>();

    private Set<String> binaryDigits = new HashSet<>();
    private Set<String> hexaDigits = new HashSet<>();

    public AFbinar() {
        Collections.addAll(binaryDigits, "0","1");
        Collections.addAll(hexaDigits, "0","1","2","3","4","5","6","7","8","9", "A", "B", "C", "D", "E", "F");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/inputFiles/AFbinar"))) {
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
            System.out.println("Error from AFbinar");
            e.printStackTrace();
        }
    }

    public boolean verifySequence(String sequence) {
        String stareInitiala = this.stareInitiala;
        String stareFinala = "";
        for (char character : sequence.toCharArray()) {
            for (Tranzitie tranzitie : tranzitii) {
                if (tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals("binaryDigit")) {
                    if (binaryDigits.contains(String.valueOf(character))) {
                        stareFinala = tranzitie.getStareFinala();
                        break;
                    }
                }
                if(tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals("0")) {
                    stareFinala = tranzitie.getStareFinala();
                    break;
                }
                if (tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals("hexaDigit")) {
                    if (hexaDigits.contains(String.valueOf(character))) {
                        stareFinala = tranzitie.getStareFinala();
                        break;
                    }
                }
                if (tranzitie.getStareInitiala().equals(stareInitiala) && tranzitie.getValoare().equals(String.valueOf(character))) {
                    stareFinala = tranzitie.getStareFinala();
                    break;
                }
            }
            if (stareFinala.isEmpty()) {
                return false;
            }
            stareInitiala = stareFinala;
            stareFinala = "";
        }
        return stariFinale.contains(stareInitiala);
    }

}
