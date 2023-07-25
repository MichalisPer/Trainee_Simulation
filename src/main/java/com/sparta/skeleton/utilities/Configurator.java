package com.sparta.skeleton.utilities;

import com.sparta.skeleton.controller.trainee.TraineeGenerator;
import com.sparta.skeleton.controller.trainingcentre.TrainingCentreGenerator;
import com.sparta.skeleton.model.simulation.SimulationSystem;
import com.sparta.skeleton.view.DisplayManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configurator {

    public static void loadConfiguration(SimulationSystem simulationSystem) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/configuration.properties"));
            int tempClientFreq = Integer.parseInt(properties.getProperty("newClientFreq"));
            int tempCentreFreq = Integer.parseInt(properties.getProperty("newCentreFreq"));
            int tempMaxBound = Integer.parseInt(properties.getProperty("traineeMax"));
            int tempMinBound = Integer.parseInt(properties.getProperty("traineeMin"));
            int tempMaxUptake = Integer.parseInt(properties.getProperty("maxTraineeUptake"));
            setConfiguration(simulationSystem, tempClientFreq, tempCentreFreq, tempMaxBound, tempMinBound, tempMaxUptake);


        } catch (IOException | NumberFormatException e) {
            DisplayManager.printErrorInPropertiesFile();
        }
    }

    public static void setConfiguration(SimulationSystem simulationSystem,
                                        int tempClientFreq, int tempCentreFreq, int tempMaxBound, int tempMinBound, int tempMaxUptake) {
        if (tempClientFreq > 0 && tempCentreFreq > 0 && tempMaxBound > 0 && tempMinBound >= 0 && tempMaxUptake > 0) {
            simulationSystem.setNewClientFreq(tempClientFreq);
            simulationSystem.setNewCentreFreq(tempCentreFreq);
            TrainingCentreGenerator.setMaxTraineeUptake(tempMaxUptake);

            if (tempMaxBound > tempMinBound) {
                TraineeGenerator.setTraineeMin(tempMinBound);
                TraineeGenerator.setTraineeMax(tempMaxBound);
            } else {
                DisplayManager.printWrongBoundsMessage();
            }
        } else {
            DisplayManager.printErrorInPropertiesFile();
        }
    }
}
