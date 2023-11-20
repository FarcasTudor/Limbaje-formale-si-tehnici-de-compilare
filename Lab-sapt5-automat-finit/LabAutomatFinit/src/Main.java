import java.util.*;
import static java.lang.System.exit;
/*
Fisierul de intrare (EBNF):
<lista_stari>
<stare_initiala>
<stari_finale>
<CONST>
<lista_tranzitii>

<lista_stari> -> <stare> | <stare> <lista_stari>
<stare> -> 'q' | CONST
<stare_initiala> -> <stare>
<stari_finale> -> <lista_stari>
<CONST> -> '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'
<lista_tranzitii> -> <tranzitie> '\n' <lista_tranzitii> | <tranzitie> '\n'
<tranzitie> -> <stare> ' ' ID|CONST ' ' <stare>
ID = ‘a’ | ‘b’ | ‘c’ | ‘d’ | ‘e’ | ‘f’ | ‘g’ | ‘h’ | ‘i’ | ‘j’ | ‘k’ | ‘l’ | ‘m’ | ‘n’ | ‘o’ | ‘p’ | ‘q’ | ‘r’ | ‘s’ | ‘t’ | ‘u’ | ‘v’ | ‘w’ | ‘x’ | ‘y’ | ‘z’
*/
public class Main {
    private static AutomatFinit automatFinit;
    private static void afisareElementeAutomatFinit() {
        Scanner scanner = new Scanner(System.in);
        String cmd = "";
        while (true) {
            System.out.println("1. Multimea starilor.\n2. Alfabetul de intrare.\n3. Tranzitiile.\n4. Starile finale.\n0. Back.\nComanda:");
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1" -> automatFinit.printMultimeStari();
                case "2" -> automatFinit.printAlfabetIntrare();
                case "3" -> automatFinit.printTranzitii();
                case "4" -> automatFinit.printStariFinale();
                case "0" -> { return; }
                default -> System.out.println("Comanda invalida!");
            }
        }
    }
    private static void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if(automatFinit.isDeterminist()){
                System.out.println("1. Afisarea elementelor automatului finit.\n2. Verifica daca o secventa e acceptata de automatul finit.\n3. Determina cel mai lung prefix dintr-o secventa data care este acceptata de automat.\n0. Exit.");
                System.out.println("Comanda:");
                String cmd = scanner.nextLine();
                switch (cmd) {
                    case "1" -> afisareElementeAutomatFinit();
                    case "2" -> {
                        System.out.println("Introduceti secventa:");
                        String secventa = scanner.nextLine();
                        if(automatFinit.verificaSecventa(secventa))
                            System.out.println("Secventa e acceptata de automat!");
                        else
                            System.out.println("Secventa nu e acceptata de automat!");
                    }
                    case "3" -> {
                        System.out.println("Introduceti secventa:");
                        String secventa = scanner.nextLine();
                        String prefix = automatFinit.celMaiLungPrefix(secventa);
                        if(prefix.equals(""))
                            System.out.println("Nu exista prefix acceptat!");
                        else
                            System.out.println("Cel mai lung prefix acceptat: " + prefix);
                    }
                    case "0" -> { return; }
                    default -> System.out.println("Comanda invalida!");
                }
            }
            else {
                System.out.println("\nAutomatul citit nu este determinist!");
                return;
            }

        }
    }
    private static void start() {
        System.out.println("Alege metoda de citire:\n\t1. Din fisier.\n\t2. De la tastatura.\n\t0. Exit.\nComanda:");
        Scanner scanner = new Scanner(System.in);
        String cmd = scanner.nextLine();
        switch (cmd) {
            case "0" -> exit(0);
            case "1" -> automatFinit = ReadInput.readFromFile();
            case "2" -> automatFinit = ReadInput.readFromKeyboard();
            default -> {
                System.out.println("Comanda invalida!");
                start();
            }
        }
    }
    public static void main(String[] args) {
        try {
            start();
            menu();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}