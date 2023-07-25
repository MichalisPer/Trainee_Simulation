package com.sparta.skeleton.controller.client;

import com.sparta.skeleton.model.client.Client;
import com.sparta.skeleton.model.trainees.Trainee;
import com.sparta.skeleton.model.trainees.TraineeStage;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

public class ClientManager {

    public static void populateClients(Deque<Trainee> trainees, Client client, int traineeUptake) {
        for (Trainee trainee: trainees.stream().
                filter(trainee -> trainee.getCurrentStage() == TraineeStage.ON_BENCH).toList()) {
            if (traineeUptake > 0 && !client.isClientFull()) {
                trainee.setCurrentStage(TraineeStage.ON_ASSIGNMENT);
                trainee.setClientID(client.getClientID());
                client.addTrainee();
                traineeUptake--;
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

    public static int generateClientUptake(Client client) {
        Random random = new Random();
        return random.nextInt(client.getTraineeRequirement());
    }
}
