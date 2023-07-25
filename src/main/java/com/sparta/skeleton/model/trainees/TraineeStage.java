package com.sparta.skeleton.model.trainees;

public enum TraineeStage {
    WAITING("Waiting"),
    IN_TRAINING("In training"),
    ON_BENCH("On bench"),
    ON_ASSIGNMENT("On assignment");

    private final String description;

    TraineeStage(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
