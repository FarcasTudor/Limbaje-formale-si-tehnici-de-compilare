import java.io.*;
import java.util.*;

public class Main {

    private static TS TSimboluri;
    private static List<FIP> fip;
    private static final AFconst AFconst = new AFconst();
    private static final AFid AFid = new AFid();
    private static final AFbinar AFbinar = new AFbinar();

    private static final Map<String, Integer> atoms = new HashMap<>();

    static {
        atoms.put("ID", 0);
        atoms.put("CONST", 1);
        atoms.put("int", 2);
        atoms.put("main", 3);
        atoms.put("(", 4);
        atoms.put(")", 5);
        atoms.put("{", 6);
        atoms.put("}", 7);
        atoms.put(",", 8);
        atoms.put(";", 9);
        atoms.put(":", 10);
        atoms.put(".", 11);
        atoms.put("/", 12);
        atoms.put("=", 13);
        atoms.put("+", 14);
        atoms.put("-", 15);
        atoms.put("*", 16);
        atoms.put("<", 17);
        atoms.put(">", 18);
        atoms.put("if", 19);
        atoms.put("else", 20);
        atoms.put("while", 21);
        atoms.put("return", 22);
        atoms.put("for", 23);
        atoms.put("char", 24);
        atoms.put("float", 25);
        atoms.put("bool", 26);
        atoms.put("true", 27);
        atoms.put("false", 28);
        atoms.put("&&", 29);
        atoms.put("||", 30);
        atoms.put("<<", 31);
        atoms.put(">>", 32);
        atoms.put("++", 33);
        atoms.put("--", 34);
        atoms.put("%", 35);
        atoms.put("&", 36);
        atoms.put("|", 37);
        atoms.put("^", 38);
        atoms.put("~", 39);
        atoms.put("?", 40);
        atoms.put("std::cin", 41);
        atoms.put("std::cout", 42);
        atoms.put("==", 43);
        atoms.put("!=", 44);
        atoms.put("+=", 45);
        atoms.put("-=", 46);
    }


    public static void writeToFileFIP(File fileName, List<FIP> fipList) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for(FIP fip : fipList) {
                bw.write(fip.toString());
                bw.newLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file");
        }
    }

    public static void writeToFileTS(File fileName, TS ts) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            ts.sort(Comparator.comparing(TS.Pair::getFirst));
            for (int i = 0; i < ts.size(); i++) {
                ts.setSecond(i, 1000 + i);
                bw.write("Simbol: " + ts.get(i).getFirst() + ", Cod: " + ts.get(i).getSecond());
                bw.newLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file");
        }
    }

    private static void addConst(String atom) {
        TS.Pair existingPair = TSimboluri.find(atom);
        if (existingPair == null) {
            TSimboluri.add(new TS.Pair(atom, 0));
        }
    }

    private static void addId(String atom) {
        TS.Pair existingPair = TSimboluri.find(atom);
        if (existingPair == null) {
            TSimboluri.add(new TS.Pair(atom, 1));
        }
    }

    public static void processFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter("atoms.out"));

            SeparareAtomi.separateAtoms(reader, writer);
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileName = "atoms.out";
        List<String> allWords = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                allWords.add(line);
                if(atoms.containsKey(line)) {
                    continue;
                }
                if(AFconst.verifySequence(line)) {
                    addConst(line);
                    writeToFileTS(new File("src/outputFiles/tabela_TS.out"), TSimboluri);
                } else if(AFid.verifySequence(line)) {
                    addId(line);
                    writeToFileTS(new File("src/outputFiles/tabela_TS.out"), TSimboluri);
                } else if(AFbinar.verifySequence(line)) {
                    addConst(line);
                    writeToFileTS(new File("src/outputFiles/tabela_TS.out"), TSimboluri);
                } else {
                    System.out.println("Error at word: " + line);
                    break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from file");
        }

        for (String word : allWords) {
            if(atoms.containsKey(word)) {
                fip.add(new FIP(word, atoms.get(word), -1));
            }
            else if (AFconst.verifySequence(word)) {
                fip.add(new FIP(word, 0, TSimboluri.find(word).getSecond()));
            } else if (AFid.verifySequence(word)) {
                fip.add(new FIP(word, 1, TSimboluri.find(word).getSecond()));
            }
        }
        writeToFileFIP(new File("src/outputFiles/tabela_FIP.out"), fip);
    }

    public static void main(String[] args) {
        TSimboluri = new TS(50);
        fip = new ArrayList<>();
        processFile("src/inputFiles/program");
    }
}