package com.sparta.skeleton.controller.trainee;

import com.sparta.skeleton.model.trainees.Trainee;
import com.sparta.skeleton.model.trainees.TraineeStage;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

public class TraineeManager {

    public static void incrementTraineeMonthCounter(Deque<Trainee> trainees) {
        for(Trainee trainee : trainees.stream().
                filter(trainee -> trainee.getCurrentStage() == TraineeStage.IN_TRAINING).toList()) {
            trainee.incrementMonthsTrained();
        }
    }

    public static void alterAndBringToFront(Deque<Trainee> trainees, Function<Trainee, Boolean> alterFunction) {
        Deque<Trainee> tempTrainees = new LinkedList<>();
        for(Trainee trainee: trainees) {
            if (alterFunction.apply(trainee)) {
                tempTrainees.addFirst(trainee);
            } else {
                tempTrainees.addLast(trainee);
            }
        }
        trainees.clear();
        trainees.addAll(tempTrainees);

    }
}
