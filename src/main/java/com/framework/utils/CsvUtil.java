package com.framework.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvUtil {

    /**
     * Writes data to a CSV file with an option to clear or append content.
     * @param filePath The file path where CSV will be saved.
     * @param data A list of string arrays representing rows.
     * @param append If true, appends to the existing file; if false, clears and writes new content.
     */
    public static void writeToCsv(String filePath, List<String[]> data, boolean append) {
        try (FileWriter writer = new FileWriter(filePath, append)) { // Control append/overwrite
            for (String[] row : data) {
                writer.append(String.join("|", row));
                writer.append("\n");
            }
            System.out.println("CSV file written successfully to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}


