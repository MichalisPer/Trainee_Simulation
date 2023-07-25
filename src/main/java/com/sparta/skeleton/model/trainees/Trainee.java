package com.sparta.skeleton.model.trainees;

import com.sparta.skeleton.utilities.TraineeHelper;

public class Trainee {

    private static int increment = 1;
    private final int traineeID;
    private final String courseType;
    private int monthsTrained = 0;

    private TraineeStage currentStage;

    private int trainingCentreID;

    private int clientID;

    public Trainee() {
        traineeID = increment;
        courseType = TraineeHelper.getRandomTraineeTypes(1)[0];
        currentStage = TraineeStage.WAITING;
        increment++;
    }

    public int getTraineeID() {
        return traineeID;
    }

    public String getCourseType() {
        return courseType;
    }

    public void incrementMonthsTrained() {
        monthsTrained++;
    }

    public int getMonthsTrained() {
        return monthsTrained;
    }

    public int getTrainingCentreID() {
        return trainingCentreID;
    }

    public TraineeStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(TraineeStage currentStage) {
        this.currentStage = currentStage;
    }

    public void setTrainingCentreID(int trainingCentreID) {
        this.trainingCentreID = trainingCentreID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
