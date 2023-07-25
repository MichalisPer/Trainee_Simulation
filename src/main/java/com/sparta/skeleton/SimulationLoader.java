package com.sparta.skeleton;

import com.sparta.skeleton.model.simulation.SimulationSystem;
import com.sparta.skeleton.utilities.InputFileReader;
import com.sparta.skeleton.utilities.UserInputVerifier;
import com.sparta.skeleton.view.DisplayManager;

import java.util.Map;
import java.util.Scanner;

/**
 * <pre>
 * <b>File name:</b> SimulationLoader.java <br>
 * <b>Purpose:</b> The SimulationLoader class facilitates the loading
 * and initiation of simulations based on user input and data from files.
 * It serves as the entry point for the simulation process,
 * facilitating user interactions and handling input options effectively.
 * </pre>
 *
 * @author Perikleous Michalis
 * @version 1.0.0
 */
public class SimulationLoader {

    /**
     * Takes all required input from the user and initiate
     * the simulation based on the given parameters.
     */
    public void start() {
        Scanner in = new Scanner(System.in);

        do { // Do-While loop to verify yearOrFile variable
            DisplayManager.displayConfigurationOptions();

            String yearOrFile = UserInputVerifier.getYearOrFile(in); // gets filename or years to run simulation from user

            boolean isNumOfYears = UserInputVerifier.isNumOfYears(yearOrFile); // check if input is number or filename

            String outputFrequency = UserInputVerifier.getOutputFrequency(in); // gets frequency input from user

            if (isNumOfYears) { // if statement to check if input is number of years
                runSimulationForSingleInput(Integer.parseInt(yearOrFile), outputFrequency);
            } else { // run simulation from file
                runSimulationFromFile(yearOrFile, outputFrequency);
            }
            DisplayManager.displayExitOption();
        } while (!in.next().equalsIgnoreCase("e"));
    }

    /**
     * Starts the simulation for given number of years and output frequency.
     *
     * @param numberOfYears   the number of years that the simulation will run
     * @param outputFrequency a String that represents the output frequency (m: monthly, y: yearly, f: final)
     */
    private static void runSimulationForSingleInput(int numberOfYears, String outputFrequency) {
        SimulationSystem simulationSystem = new SimulationSystem();
        simulationSystem.runSimulation(numberOfYears, outputFrequency);
    }

    /**
     * Reads the data from the input file and runs the simulation for the different scenarios.
     *
     * @param filename        the name of the file that contains the data
     * @param outputFrequency a String that represents the output frequency (m: monthly, y: yearly, f: final)
     */
    private void runSimulationFromFile(String filename, String outputFrequency) {
        Map<Integer, Integer> yearsAndRepetitions = InputFileReader.readInputFile(filename);
        for (Integer years : yearsAndRepetitions.keySet()) {
            for (int i = 0; i < yearsAndRepetitions.get(years); i++) {
                runSimulationForSingleInput(years, outputFrequency);
            }
        }
    }
}
