import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadInput {
    private static final Set<String> stari = new TreeSet<>();
    private static final Set<String> alfabetIntrare = new HashSet<>();
    private static final List<Tranzitie> tranzitii = new ArrayList<>();
    private static final Set<String> stariFinale = new HashSet<>();
    static AutomatFinit readFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"))) {
            String[] data = bufferedReader.readLine().split(" ");
            Collections.addAll(stari, data);

            String stareInitiala = bufferedReader.readLine();

            data = bufferedReader.readLine().split(" ");
            Collections.addAll(stariFinale, data);

            int numarTranzitii = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < numarTranzitii; i++) {
                data = bufferedReader.readLine().split(" ");
                tranzitii.add(new Tranzitie(data[0], data[2], data[1]));
                alfabetIntrare.add(data[1]);
            }

            AutomatFinit automatFinit = new AutomatFinit(stari, alfabetIntrare, tranzitii, stareInitiala, stariFinale);
            System.out.println("Datele s-au citit din fisier cu succes!");
            return automatFinit;
        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului!");
            e.printStackTrace();
        }
        return null;
    }
    static AutomatFinit readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti multimea starilor separate printr-un spatiu: ");
        Collections.addAll(stari, scanner.nextLine().split(" "));

        System.out.println("Introduceti starea initiala: ");
        String stareInitiala = scanner.nextLine();

        System.out.println("Introduceti multimea starilor finale separate printr-un spatiu: ");
        Collections.addAll(stariFinale, scanner.nextLine().split(" "));

        System.out.println("Introduceti numarul de tranzitii: ");
        int numarTranzitii = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numarTranzitii; i++) {
            System.out.println("Introduceti tranzitia " + (i + 1) + " sub forma: <stareInitiala> <valoare> <stareFinala>");
            String[] data = scanner.nextLine().split(" ");
            tranzitii.add(new Tranzitie(data[0], data[2], data[1]));
            alfabetIntrare.add(data[1]);
        }
        AutomatFinit automatFinit = new AutomatFinit(stari, alfabetIntrare, tranzitii, stareInitiala, stariFinale);
        System.out.println("Datele s-au citit de la tastatura cu succes!");
        return automatFinit;
    }
}
