package com.sparta.skeleton.controller.client;

import com.sparta.skeleton.model.client.Client;
import com.sparta.skeleton.model.trainees.Trainee;
import com.sparta.skeleton.model.trainees.TraineeStage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Random;

public class ClientManager {

    public static void populateClients(Deque<Trainee> trainees, Client client) {
        for (Trainee trainee: trainees.stream().
                filter(trainee -> trainee.getCurrentStage() == TraineeStage.ON_BENCH &&
                        Arrays.stream(client.getRequiredTraineeType()).anyMatch(s -> s.equals(trainee.getCourseType()))).toList()) {
            if (!client.isClientFull()) {
                trainee.setCurrentStage(TraineeStage.ON_ASSIGNMENT);
                trainee.setClientID(client.getClientID());
                client.addTrainee();
            } else {
                break;
            }
        }
    }

    public static void incrementClientMonth(ArrayList<Client> clients) {
        for (Client client : clients) {
            client.incrementMonth();
        }
    }
}
