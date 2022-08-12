package de.hgbp.denner.julian.translatorgui;

import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {

    static Map<String, String> dict;

    public static void init_Translator(File ini_file, Stage stage) {

        ArrayList<String> translations = new ArrayList<>();


        // Get all translations from file and catch err
        try {
            translations = readFromInputStream(new FileInputStream(ini_file));
        } catch (Exception ex) {
            MainApplication.fileReadErr(stage);
        }
        dict = parseDictionary(translations);
        System.out.println(dict);
        while (dict.isEmpty()) {
            ini_file = MainApplication.fileEmptyErr(stage);
            try {
                translations = readFromInputStream(new FileInputStream(ini_file));
            } catch (Exception ex) {
                MainApplication.fileReadErr(stage);
            }
            dict = parseDictionary(translations);
        }
    }


    public static String translate_string(String input) {
        String finalTranslation = "";

        // Split all words in sentence and loop trough them
        for (String userInputWord : input.split(" ")) {
            finalTranslation += " " + translate(userInputWord, dict);
        }
        return finalTranslation;
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