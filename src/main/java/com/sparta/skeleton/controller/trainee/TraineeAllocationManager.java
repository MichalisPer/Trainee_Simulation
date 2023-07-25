package com.sparta.skeleton.controller.trainee;

import com.sparta.skeleton.controller.client.ClientManager;
import com.sparta.skeleton.controller.trainingcentre.TrainingCentreManager;
import com.sparta.skeleton.model.client.Client;
import com.sparta.skeleton.model.trainees.Trainee;
import com.sparta.skeleton.model.trainees.TraineeStage;
import com.sparta.skeleton.model.trainingCentres.TrainingCentre;


import java.util.*;

public class TraineeAllocationManager {

    public static void allocate(Deque<Trainee> trainees, ArrayList<TrainingCentre> centres) {
        if (trainees.stream().anyMatch(trainee -> trainee.getCurrentStage() == TraineeStage.WAITING)) {
            allocateToTrainingCentres(trainees, centres);
        }
    }

    public static void allocateToTrainingCentres(Deque<Trainee> trainees, ArrayList<TrainingCentre> centres) {
        int traineeUptake = 0;
        for (TrainingCentre centre : centres) { // fill each training centre before going to the next centre
            if (centre.trainingCentreIsFull() || centre.isClosed()) {
                continue;
            }
            traineeUptake += TrainingCentreManager.generateRandomTraineeUptake();
            traineeUptake = TrainingCentreManager.populateTrainingCentre(trainees, centre, traineeUptake);
            if (traineeUptake > 0 && !centre.trainingCentreIsFull()) {
                break;
            }
        }
    }

    public static void benchTrainees(Deque<Trainee> trainees, ArrayList<TrainingCentre> trainingCentres) {
        for (Trainee trainee : trainees.stream().
                filter(trainee -> trainee.getCurrentStage() == TraineeStage.IN_TRAINING).toList()) {
            if (trainee.getMonthsTrained() >= 12) {
                trainee.setCurrentStage(TraineeStage.ON_BENCH);
                trainingCentres.stream().filter(trainingCentre ->
                        trainingCentre.getTrainingCentreID() == trainee.getTrainingCentreID())
                        .findFirst()
                        .ifPresent(TrainingCentre::removeTrainee);
            }
        }
    }

    public static void allocateToClients(Deque<Trainee> graduates, ArrayList<Client> clients) {
        for (Client client : clients) { // fill each training centre before going to the next centre
            if (!client.isHappy()) {
                continue;
            }
            if (client.getCountMonths() % 12 == 0) {
                client.setCurrentTraineeRequirement();
            }
            ClientManager.populateClients(graduates, client);
        }
    }
}