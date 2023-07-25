package com.sparta.skeleton.controller.trainingcentre;

import com.sparta.skeleton.model.trainingCentres.Bootcamp;
import com.sparta.skeleton.model.trainingCentres.TechCentre;
import com.sparta.skeleton.model.trainingCentres.TrainingCentre;
import com.sparta.skeleton.model.trainingCentres.TrainingHub;

import java.util.ArrayList;
import java.util.Random;

public class TrainingCentreGenerator {
    public static final int BOOTCAMP_MAX = 2;

    public static int maxTraineeUptake;
    public static void generateTrainingCentre(ArrayList<TrainingCentre> trainingCentres) throws RuntimeException {
        Random rand = new Random();
        int nextCenter;

        if (trainingCentres.stream().filter(trainingCentre ->
                trainingCentre.getType().equals("Bootcamp") && !trainingCentre.isClosed()).count() >= BOOTCAMP_MAX) {
            nextCenter = rand.nextInt(2);
        } else {
            nextCenter = rand.nextInt(3);
        }

        switch (nextCenter) {
            case 0 -> {
                for (int i = 0; i < 3; i++) {
                    trainingCentres.add(new TrainingHub());
                }
            }
            case 1 -> trainingCentres.add(new TechCentre());
            case 2 -> trainingCentres.add(new Bootcamp());
            default -> throw new RuntimeException("Invalid case");
        }
    }

    public static void setMaxTraineeUptake(int maxTraineeUptake) {
        TrainingCentreGenerator.maxTraineeUptake = maxTraineeUptake;
    }

    public static int generateRandomTraineeUptake() {
        return new Random().nextInt(maxTraineeUptake + 1);
    }
}
