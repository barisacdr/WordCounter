package com.mycompany.app;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException, CmdLineException {

        Options options = new Options();
        CmdLineParser parser = new CmdLineParser(options);
        parser.parseArgument(args);
        List<String> words = getWords(options.unique, options.fileNames);
        ArrayList<String> wordsToBeSorted = parseToWords(options);

        if (options.unique) {
            words = new ArrayList<>(new HashSet<>(words));
        }

        Collator trCollator = Collator.getInstance(Locale.forLanguageTag("EN-en"));
        trCollator.setStrength(Collator.PRIMARY);

        Collections.sort(wordsToBeSorted, trCollator);

        if (options.reverse)
            Collections.reverse(wordsToBeSorted);

        int length = 5;

        if (options.topN > 0)
            length = options.topN;

        switch (options.task.toLowerCase()) {
            case "numoftokens":
                System.out.println(words.size());
                break;
            case "frequentterms":
                Map<String, Integer> wordCount = countWords(words);
                printFrequentTerms(wordCount, options.topN);
                break;
            case "termlengthstats":
                int maxLength = words.get(0).length();
                int minLength = words.get(0).length();
                int total = 0;
                for (String word : words) {
                    if (word.length() > maxLength)
                        maxLength = word.length();
                    if (word.length() < minLength)
                        minLength = word.length();

                    total += word.length();
                }

                double d = total;

                System.out.println("Max Token Length in Character: " + maxLength
                        + ", Min Token Length: " + minLength + ", Average Token Length: " + (d / words.size()));
            case "termsstartwith":
                    for (String word : wordsToBeSorted) {
                        if (length == 0)
                            return;
                        try {
                            if (word.startsWith(options.startWith)) {
                                System.out.println(word);
                                length--;
                            }
                        }
                        catch(NullPointerException e) {
                            return;
                        }

                   }
        }
    }

    public static List<String> getWords(boolean unique, String... fileNames) throws IOException {
        List<Path> paths = new ArrayList<>();
        for (String fileName : fileNames)
            paths.add(Paths.get(fileName));
        List<String> words = new ArrayList<>();
        for (Path path : paths) {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken();
                    StringBuilder builder = new StringBuilder();
                    for (char c : token.toCharArray())
                        if (Character.isLetterOrDigit(c))
                            builder.append(Character.toLowerCase(c));
                    String result = builder.toString();
                    if (!result.trim().isEmpty()) words.add(result);
                }
            }
        }
        return unique ? new ArrayList<>(new HashSet<>(words)) : words;
    }

    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> res = new HashMap<>();
        for (String word : words) {
            if (res.containsKey(word))
                res.replace(word, res.get(word) + 1);
            else res.put(word, 1);
        }
        return res;
    }

    public static void printFrequentTerms(Map<String, Integer> map, int topN) {
        if (Options.reverse) {
            for (int i = topN; i > 0; i--) {
                Map.Entry<String, Integer> minValue = null;
                for (Map.Entry<String, Integer> entry : map.entrySet())
                    if (minValue == null || entry.getValue() < minValue.getValue())
                        minValue = entry;
                if (minValue != null)
                    map.remove(minValue.getKey());
                else return;
                System.out.println(minValue.getKey() + "   " + minValue.getValue());
            }
        } else {
            for (int i = 0; i < topN; i++) {
                Map.Entry<String, Integer> maxValue = null;
                for (Map.Entry<String, Integer> entry : map.entrySet())
                    if (maxValue == null || entry.getValue() > maxValue.getValue())
                        maxValue = entry;
                if (maxValue != null)
                    map.remove(maxValue.getKey());
                else return;

                System.out.println(maxValue.getKey() + "   " + maxValue.getValue());
            }
        }
    }

    public static ArrayList<String> parseToWords(Options bean) {
        ArrayList<String> words = new ArrayList<>();

        for (String fileName : bean.fileNames) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(line, " ");
                    while (st.hasMoreTokens()) {
                        String word = st.nextToken();
                        word = word.toLowerCase(Locale.ENGLISH);

                        String temp = "";

                        for (int i = 0; i < word.length(); i++) {
                            if (Character.isLetterOrDigit(word.charAt(i))) {
                                temp += word.charAt(i);
                            }
                        }

                        if (temp.equals("") || temp.equals(" "))
                            continue;

                        words.add(temp);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return words;
    }
}
