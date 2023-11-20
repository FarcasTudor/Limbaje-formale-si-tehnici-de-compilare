import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SeparareAtomi {
    static boolean isSeparator(char ch) {
        return ch == ';' || ch == ',' || ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == '=' || ch == ' ' || ch =='+' || ch == '-' || ch =='<' || ch == '>' || ch == '\t';
    }
    public static void separateAtoms(BufferedReader reader, BufferedWriter writer) throws IOException {
        StringBuilder currentWord = new StringBuilder();
        int ch;

        while ((ch = reader.read()) != -1) {
            if (isSeparator((char) ch)) {
                if(currentWord.toString().equals("==") || currentWord.toString().equals("!=")) {
                    writer.write(currentWord.toString() + '\n');
                    currentWord = new StringBuilder();
                }
                else if (currentWord.length() > 0) {
                    if(ch == ' ' && (currentWord.toString().equals("+") || currentWord.toString().equals("-") || currentWord.toString().equals("="))) {
                        writer.write(currentWord.toString() + '\n');
                        currentWord = new StringBuilder();
                    }
                    else {
                        if(!currentWord.toString().equals("+") && !currentWord.toString().equals("-") && !currentWord.toString().equals("="))
                        {
                            writer.write(currentWord.toString() + '\n');
                            currentWord = new StringBuilder();
                        }
                    }
                }

                if (ch == '(' || ch == ')' || ch == ',' || ch == '=' || ch == '!' || ch == ';' || ch == '{' || ch == '}') {
                    if(ch == '=' || ch == '!'){
                        currentWord.append((char) ch);
                    }
                    else {
                        currentWord = new StringBuilder();
                        writer.write((char) ch + "\n");
                    }
                } else if (ch != ' ' && ch != '\t') {
                    currentWord.append((char) ch);
                }
            } else {
                if (ch != '\n' && ch != '\r') {
                    if(currentWord.toString().equals("=") || currentWord.toString().equals("<") || currentWord.toString().equals(">")) {
                        writer.write(currentWord.toString() + '\n');
                        currentWord=  new StringBuilder();
                        currentWord.append((char)ch);
                    }
                    else
                        currentWord.append((char) ch);
                } else if (currentWord.length() > 0) {
                    writer.write(currentWord.toString() + '\n');
                    currentWord = new StringBuilder();
                }
            }
        }

        if (currentWord.length() > 0) {
            writer.write(currentWord.toString() + '\n');
        }
    }
}
