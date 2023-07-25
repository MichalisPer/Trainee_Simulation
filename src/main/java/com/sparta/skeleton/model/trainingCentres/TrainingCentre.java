package com.sparta.skeleton.model.trainingCentres;

import com.sparta.skeleton.model.trainees.Trainee;

import java.util.ArrayList;

public abstract class TrainingCentre {
    protected final int trainingCentreID;
    protected static int increment = 1;
    protected int countMonths = 0;
    protected int numOfTrainees = 0;
    protected String[] courseTypes;

    private boolean isClosed = false;

    public TrainingCentre() {
        trainingCentreID = increment;
        increment++;
    }

    public int getCurrentCapacity() {
        return numOfTrainees;
    }
    public int getRemainingCapacity() {
        return getMaxCapacity()- numOfTrainees;
    }

    public boolean trainingCentreIsFull() {
        return getMaxCapacity() == numOfTrainees;
    }

    public void addTrainee() {
        if(numOfTrainees < getMaxCapacity()) {
            numOfTrainees++;
        }
    }

    public int getTrainingCentreID() {
        return trainingCentreID;
    }

    public int getNumOfTrainees() {
        return numOfTrainees;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void closeCentre(){
        isClosed = true;
    }

    public void incrementMonth() {
        countMonths++;
    }

    public boolean isOverMaxMonths() {
        return countMonths >= getMaxMonths();
    }

    public String[] getCourseTypes() {
        return courseTypes;
    }

    public void setCourseType(String[] courseType) {
        this.courseTypes = courseType;
    }

    public abstract int getMaxMonths();

    public abstract int getMaxCapacity();


}
