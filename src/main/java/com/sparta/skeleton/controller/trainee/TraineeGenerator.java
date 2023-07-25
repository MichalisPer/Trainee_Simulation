package com.sparta.skeleton.controller.trainee;


import com.sparta.skeleton.model.trainees.Trainee;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TraineeGenerator {

    private static int traineeMax;
    private static int traineeMin;

    public static void generateTrainees(Deque<Trainee> trainees) {
        Random random = new Random();
        for (int i = 0; i < random.nextInt(traineeMin, traineeMax + 1); i++) {
            trainees.add(new Trainee());
        }
    }

    public static void setTraineeMax(int traineeMax) {
        TraineeGenerator.traineeMax = traineeMax;
    }

    public static void setTraineeMin(int traineeMin) {
        TraineeGenerator.traineeMin = traineeMin;
    }
}
