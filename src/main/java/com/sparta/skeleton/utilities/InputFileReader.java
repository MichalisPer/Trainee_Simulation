package com.sparta.skeleton.utilities;

import com.sparta.skeleton.view.DisplayManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputFileReader {

    private static final String PATH_PREFIX = "src/main/resources/"; // Prefix to locate input file

    /**
     * Reads the data of the input file and creates a map where
     * key:value pairs are years:repetitions to run the simulation.
     *
     * @param filename the name of the file that contains the data
     * @return a map where key:value pairs represent years:repetitions
     */
    public static Map<Integer, Integer> readInputFile(String filename) {
        Map<Integer, Integer> yearsAndRepetitions = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_PREFIX + filename))) {
            String line;
            int lineCounter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().startsWith("#")) continue;
                lineCounter++;
                String[] tokens = line.split(" ");
                int numOfYears;
                int repetitions = 1;
                try {
                    numOfYears = Integer.parseInt(tokens[0]);

                    if (tokens.length > 1) {
                        repetitions = Integer.parseInt(tokens[1]);
                    }
                } catch (NumberFormatException e) {
                    DisplayManager.printErrorInFile(line.split(" ")[0], lineCounter);
                    continue;
                }
                yearsAndRepetitions.put(numOfYears, repetitions);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return yearsAndRepetitions;
    }
}
