package com.sparta.skeleton.model.client;

import com.sparta.skeleton.utilities.NonGaussianRandomBias;
import com.sparta.skeleton.utilities.TraineeHelper;

public class Client {

    private static int increment = 1;

    private final int clientID;
    private int countMonths = 0;
    private int traineesOnSite = 0;

    private final int traineeRequirement;

    private int currentTraineeRequirement;

    private final String[] requiredTraineeType;

    private boolean isHappy = true;

    public Client() {
        clientID = increment;
        requiredTraineeType = TraineeHelper.getRandomTraineeTypes(1);
        traineeRequirement = NonGaussianRandomBias.randomBiasGenerator();
        currentTraineeRequirement = traineeRequirement;
        increment++;
    }

    public int getTraineesOnSite() {
        return traineesOnSite;
    }

    public int getCountMonths() {
        return countMonths;
    }

    public boolean isHappy() {
        if ((countMonths % 12 != 0 || traineesOnSite >= currentTraineeRequirement) && isHappy) {
            return true;
        } else {
            isHappy = false;
            return false;
        }
    }

    public boolean isClientFull() {
        return traineesOnSite == currentTraineeRequirement;
    }

    public void setCurrentTraineeRequirement() {
        currentTraineeRequirement += traineeRequirement;
    }

    public void incrementMonth() {
        countMonths++;
    }

    public int getTraineeRequirement() {
        return traineeRequirement;
    }

    public String[] getRequiredTraineeType() {
        return requiredTraineeType;
    }

    public boolean addTrainee() {
        if(traineesOnSite < currentTraineeRequirement) {
            traineesOnSite++;
            return true;
        } else {
            return false;
        }
    }

    public int getCurrentTraineeRequirement() {
        return currentTraineeRequirement;
    }

    public int getClientID() {
        return clientID;
    }

}
