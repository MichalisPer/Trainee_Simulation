package com.sparta.skeleton;

import com.sparta.skeleton.model.simulation.SimulationSystem;
import com.sparta.skeleton.utilities.InputFileReader;
import com.sparta.skeleton.utilities.logging.LoggerSingleton;
import com.sparta.skeleton.view.DisplayManager;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class SimulationLoader {

    Logger logger = LoggerSingleton.getSingleton().getLogger();

    /**
     * Takes all required input from the user and initiate
     * the simulation based on the given parameters.
     * */
    public void start() {
        logger.log(Level.INFO, "\n \n NEW SIMULATION HAS STARTED");
        Scanner in = new Scanner(System.in);

        String userInput; // stores the filename or number of years given by the user
        boolean isNumOfYears; // checks if the user input is number of years (true) or filename (false)
        do { // Do-While loop to verify userInput variable
            DisplayManager.displayConfigurationOptions();
            do {
                Pattern pattern = Pattern.compile("[0-9]+|.*.txt");
                try {
                    userInput = in.next(pattern);
                    pattern = Pattern.compile("[0-9]+");
                    isNumOfYears = pattern.matcher(userInput).matches();
                    break;
                } catch (InputMismatchException e) {
                    DisplayManager.printWrongInputMessage(in.next());
                    in.nextLine();
                }
            } while (true);

            String outputFrequency; // stores the preference of user for frequency of displaying output
            DisplayManager.displayOutputOptions();
            do { // Do-While loop to verify the user input for outputFrequency
                try {
                    Pattern pattern = Pattern.compile("[ymf]");
                    outputFrequency = in.next(pattern);
                    break;
                } catch (InputMismatchException e) {
                    DisplayManager.printWrongFrequencyInput(in.next());
                    in.nextLine();
                }
            } while (true);


            if (isNumOfYears) { // if statement to check if input is number of years
                runSimulationForSingleInput(Integer.parseInt(userInput), outputFrequency);
            } else { // run simulation from file
                runSimulationFromFile(userInput, outputFrequency);
            }
            DisplayManager.displayExitOption();
        } while (!in.next().equalsIgnoreCase("e"));
    }

    /**
     * Starts the simulation for given number of years and output frequency.
     *
     * @param numberOfYears the number of years that the simulation will run
     * @param outputFrequency a String that represents the output frequency (m: monthly, y: yearly, f: final)
     */
    private static void runSimulationForSingleInput(int numberOfYears, String outputFrequency) {
        SimulationSystem simulationSystem = new SimulationSystem();
        simulationSystem.runSimulation(numberOfYears, outputFrequency);
    }

    /**
     * Reads the data from the input file and runs the simulation for the different scenarios.
     *
     * @param filename the name of the file that contains the data
     * @param outputFrequency a String that represents the output frequency (m: monthly, y: yearly, f: final)
     */
    private void runSimulationFromFile(String filename, String outputFrequency) {
        Map<Integer, Integer> yearsAndRepetitions = InputFileReader.readInputFile(filename);
        for(Integer years : yearsAndRepetitions.keySet()) {
            for (int i = 0; i < yearsAndRepetitions.get(years); i++) {
                runSimulationForSingleInput(years, outputFrequency);
            }
        }
    }

}
