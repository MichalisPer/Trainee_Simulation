package com.sparta.skeleton.model.simulation;

import com.sparta.skeleton.model.trainees.TraineeStage;
import com.sparta.skeleton.model.trainingCentres.TrainingCentre;
import com.sparta.skeleton.utilities.TraineeHelper;
import com.sparta.skeleton.utilities.TrainingCentreHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JSONFileWriter {

    private static int fileCounter = 1;

    private static FileWriter fileWriter;

    private static final JSONArray finalObj = new JSONArray();

    public static void exportToJSON(SimulationSystem simulationSystem, int duration, String outputFrequency) {
        JSONObject currentPeriod = new JSONObject();

        JSONObject openCentres = new JSONObject();
        openCentres.put("total", simulationSystem.trainingCentres.size());
        for (String trainingCentreType : TrainingCentreHelper.TRAINING_CENTRE_TYPES) {
            openCentres.put(trainingCentreType, simulationSystem.getNumberOfTrainingCentresByType(simulationSystem.trainingCentres, trainingCentreType));
        }
        currentPeriod.put("open_centres", openCentres);
        JSONObject fullCentres = new JSONObject();
        fullCentres.put("total", simulationSystem.getListOfFullTrainingCentres().size());
        for (String trainingCentreType : TrainingCentreHelper.TRAINING_CENTRE_TYPES) {
            fullCentres.put(trainingCentreType, simulationSystem.getNumberOfTrainingCentresByType(simulationSystem.getListOfFullTrainingCentres(), trainingCentreType));
        }
        currentPeriod.put("full_centres", fullCentres);

        JSONObject closedCentres = new JSONObject();
        closedCentres.put("total", simulationSystem.trainingCentres.stream().filter(TrainingCentre::isClosed).count());
        for (String trainingCentreType : TrainingCentreHelper.TRAINING_CENTRE_TYPES) {
            closedCentres.put(trainingCentreType, simulationSystem.getNumberOfTrainingCentresByType(simulationSystem.trainingCentres.stream().filter(TrainingCentre::isClosed).toList(), trainingCentreType));
        }
        currentPeriod.put("closed_centres", closedCentres);

        for(TraineeStage stage: TraineeStage.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", simulationSystem.getNumberOfTrainees(stage));
            for (String traineeCourse : TraineeHelper.TRAINEE_TYPES) {
                jsonObject.put(traineeCourse, simulationSystem.getNumberOfTrainees(stage, traineeCourse));
            }
            currentPeriod.put(stage.getDescription(), jsonObject);
        }

        JSONObject happyClients = new JSONObject();
        happyClients.put("total", simulationSystem.getListOfHappyClients().size());
        for (String traineeCourse : TraineeHelper.TRAINEE_TYPES) {
            happyClients.put(traineeCourse, simulationSystem.getNumberOfClientsByType(simulationSystem.getListOfHappyClients(), traineeCourse));
        }
        currentPeriod.put("happy_clients", happyClients);

        JSONObject unhappyClients = new JSONObject();
        unhappyClients.put("total", simulationSystem.getListOfUnhappyClients().size());
        for (String traineeCourse : TraineeHelper.TRAINEE_TYPES) {
            unhappyClients.put(traineeCourse, simulationSystem.getNumberOfClientsByType(simulationSystem.getListOfUnhappyClients(), traineeCourse));
        }
        currentPeriod.put("unhappy_clients", unhappyClients);

        switch (outputFrequency) {
            case "m" -> {
                currentPeriod.put("month", duration);
                finalObj.put(duration - 1, currentPeriod);
            }
            case "y" -> {
                currentPeriod.put("year", duration);
                finalObj.put(duration - 1, currentPeriod);
            }
            case "f" -> {
                currentPeriod.put("year", duration);
                finalObj.put(0, currentPeriod);
            }
        }
    }

    public static void openJSONFile() {
        try {
            String filename = "simulationOutput" + fileCounter + ".json";
            fileWriter = new FileWriter("src/main/resources/" + filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileCounter++;
    }

    public static void closeJSONFile() {
        try {
            fileWriter.write(finalObj.toString(4));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
