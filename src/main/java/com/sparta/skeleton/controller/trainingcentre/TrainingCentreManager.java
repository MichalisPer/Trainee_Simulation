package com.sparta.skeleton.controller.trainingcentre;

import com.sparta.skeleton.controller.trainee.TraineeManager;
import com.sparta.skeleton.model.trainees.Trainee;
import com.sparta.skeleton.model.trainees.TraineeStage;
import com.sparta.skeleton.model.trainingCentres.TrainingCentre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

public class TrainingCentreManager {


    public static void close(ArrayList<TrainingCentre> centres, Deque<Trainee> trainees) {
        for (TrainingCentre centre : centres.stream().filter(trainingCentre -> !trainingCentre.isClosed()).toList()) { // check each centre that needs to be closed
            if (centre.getCurrentCapacity() < 25 && centre.isOverMaxMonths() && !centre.isClosed()) {
                TraineeManager.alterAndBringToFront(trainees, trainee -> {
                    if (trainee.getTrainingCentreID() == centre.getTrainingCentreID()
                            && trainee.getCurrentStage() == TraineeStage.IN_TRAINING) {
                        trainee.setTrainingCentreID(-1);
                        trainee.setCurrentStage(TraineeStage.WAITING);
                        return true;
                    }
                    return false;
                });
                centre.closeCentre();
            }
        }
    }

    public static int populateTrainingCentre(Deque<Trainee> traineeQueue, TrainingCentre trainingCentre, int uptake) {
        for (Trainee trainee : traineeQueue.stream()
                .filter(trainee -> trainee.getCurrentStage() == TraineeStage.WAITING
                        && Arrays.stream(trainingCentre.getCourseTypes()).anyMatch(s -> s.equals(trainee.getCourseType()))).toList()) {
            if (uptake > 0 && !trainingCentre.trainingCentreIsFull()) {
                trainee.setTrainingCentreID(trainingCentre.getTrainingCentreID());
                trainee.setCurrentStage(TraineeStage.IN_TRAINING);
                trainingCentre.addTrainee();
                uptake--;
            } else {
                break;
            }
        }
        return uptake;
    }

    public static void incrementMonthCounter(ArrayList<TrainingCentre> trainingCentres) {
        for (TrainingCentre centre : trainingCentres.stream().
                filter(trainingCentre -> !trainingCentre.isClosed()).toList()) {
            centre.incrementMonth();
        }
    }

}
