package com.sparta.skeleton.model.trainees;

public enum TraineeStage {
    WAITING("waiting"),
    IN_TRAINING("in training"),
    ON_BENCH("on bench"),
    ON_ASSIGNMENT("on assignment");

    private final String description;

    TraineeStage(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
