import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Program {
    public static void main(String[] args) {

        ArrayList<String> translations = new ArrayList<>();
        // Get all translations from file and catch err
        try {
            translations = readFromInputStream(new FileInputStream("translations.ini"));
        } catch (Exception ex) {
            System.err.println(ex);
        }

        Map<String, String> dict = parseDictionary(translations);

        while (true) {
            String finalTranslation = "";
            System.out.println("\n\nEnter Word: ");
            // Read user input
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String userInput = inputReader.readLine();

                // Command to quit the program
                if (userInput.equalsIgnoreCase(":q")) {
                    System.exit(0);
                }

                // Split all words in sentence and loop trough them
                for (String userInputWord : userInput.split(" ")) {
                    finalTranslation += " " + translate(userInputWord, dict);
                }

                clearScreen();
                System.out.println("Translation: \n" + finalTranslation);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }

    }

    public static ArrayList<String> readFromInputStream(FileInputStream fis) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static Map<String, String> parseDictionary(ArrayList<String> translations) {
        Map<String, String> dictionary = new HashMap<>();
        for (String item : translations) {
            if (!item.isBlank()) {
                String[] translation = item.split("=");
                dictionary.put(translation[0].toLowerCase(), translation[1]);
            }
        }
        return dictionary;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String translate(String input, Map<String, String> dict) {
        // TODO: Handle special symbols mid-word or at the beginning
        String specialSymbols = "";
        String translation = "";
        // Compile regex for filtering special symbols
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        Matcher m = p.matcher(input);
        // Saving special symbols in a variable to append them later
        while (m.find()) {
            specialSymbols += m.group();
        }
        // Clean input from special symbols for search in dictionary
        input = input.replaceAll("[^a-zA-Z0-9]", "");
        translation = dict.get(input.toLowerCase());
        if (translation != null) {
            return translation + specialSymbols;
        } else {
            // Return original word if no translation is found
            return input + specialSymbols;
        }
    }

}