package com.sparta.skeleton.model.simulation;

import com.sparta.skeleton.model.client.Client;
import com.sparta.skeleton.model.trainees.TraineeStage;
import com.sparta.skeleton.model.trainingCentres.TrainingCentre;
import com.sparta.skeleton.utilities.TraineeHelper;
import com.sparta.skeleton.utilities.TrainingCentreHelper;

import java.util.List;

public class SimulationOutputGenerator {

    public static String getSimulationOutput(SimulationSystem simulationSystem) {
        StringBuilder sb = new StringBuilder();

        sb.append("Number of open centres: ").append(simulationSystem.trainingCentres.stream().filter(trainingCentre -> !trainingCentre.isClosed()).count());
        formatOutputByTrainingCentreType(simulationSystem, simulationSystem.trainingCentres.stream().filter(trainingCentre -> !trainingCentre.isClosed()).toList(), sb);


        sb.append("\nNumber of full centres: ").append(simulationSystem.getListOfFullTrainingCentres().size());
        formatOutputByTrainingCentreType(simulationSystem, simulationSystem.getListOfFullTrainingCentres(), sb);

        sb.append("\nNumber of closed centres: ").append(simulationSystem.trainingCentres.stream().filter(TrainingCentre::isClosed).count());
        formatOutputByTrainingCentreType(simulationSystem, simulationSystem.trainingCentres.stream().filter(TrainingCentre::isClosed).toList(), sb);

        for (TraineeStage stage : TraineeStage.values()) {
            sb.append("\nNumber of trainees currently ").append(stage.getDescription()).append(": ").append(simulationSystem.getNumberOfTrainees(stage));
            for (String traineeCourse : TraineeHelper.TRAINEE_TYPES) {
                sb.append("\n  - ").append(traineeCourse).append(": ").append(simulationSystem.getNumberOfTrainees(stage, traineeCourse));
            }
        }

        sb.append("\nNumber of happy clients: ").append(simulationSystem.getListOfHappyClients().size());
        formatClientOutputByTraineeType(simulationSystem, simulationSystem.getListOfHappyClients(), sb);

        sb.append("\nNumber of unhappy clients: ").append(simulationSystem.getListOfUnhappyClients().size());
        formatClientOutputByTraineeType(simulationSystem, simulationSystem.getListOfUnhappyClients(), sb);
        return sb.toString();
    }

    private static void formatOutputByTrainingCentreType(SimulationSystem simulationSystem, List<TrainingCentre> trainingCentres, StringBuilder sb) {
        for (String trainingCentreType : TrainingCentreHelper.TRAINING_CENTRE_TYPES) {
            sb.append("\n  - ").append(trainingCentreType).append(": ").append(simulationSystem.getNumberOfTrainingCentresByType(trainingCentres, trainingCentreType));
        }
    }

    private static void formatClientOutputByTraineeType(SimulationSystem simulationSystem, List<Client> clients, StringBuilder sb) {
        for (String traineeType : TraineeHelper.TRAINEE_TYPES) {
            sb.append("\n  - ").append(traineeType).append(": ").append(simulationSystem.getNumberOfClientsByType(clients, traineeType));
        }
    }
}
