 package utilies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

    public class CSVFileManager {

        public static Map<String, List<String>> getMostFrequentWords(String filePath) {

            Map<String, List<String>> result = new LinkedHashMap<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

                // Read header
                String headerLine = reader.readLine();

                if (headerLine == null) {
                    return result;
                }

                String[] headers = headerLine.split(",");

                // Create list for every column
                List<List<String>> columns = new ArrayList<>();

                for (int i = 0; i < headers.length; i++) {
                    columns.add(new ArrayList<>());
                }

                // Read data
                String line;

                while ((line = reader.readLine()) != null) {

                    String[] values = line.split(",");

                    for (int i = 0; i < values.length && i < columns.size(); i++) {

                        String word = values[i].trim();

                        if (!word.isEmpty()) {
                            columns.get(i).add(word);
                        }
                    }
                }

                // Find most frequent word in every column
                for (int i = 0; i < headers.length; i++) {

                    Map<String, Integer> frequency = new HashMap<>();

                    for (String word : columns.get(i)) {
                        frequency.put(
                                word,
                                frequency.getOrDefault(word, 0) + 1
                        );
                    }

                    if (frequency.isEmpty()) {
                        result.put(headers[i], new ArrayList<>());
                        continue;
                    }

                    int maxFrequency = Collections.max(frequency.values());

                    List<String> mostFrequentWords = new ArrayList<>();

                    for (Map.Entry<String, Integer> entry : frequency.entrySet()) {

                        if (entry.getValue() == maxFrequency) {
                            mostFrequentWords.add(entry.getKey());
                        }
                    }

                    result.put(headers[i], mostFrequentWords);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return result;
        }
    }
