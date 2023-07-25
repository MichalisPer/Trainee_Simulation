package com.sparta.skeleton.model.simulation;

import com.sparta.skeleton.controller.client.ClientGenerator;
import com.sparta.skeleton.controller.client.ClientManager;
import com.sparta.skeleton.controller.trainee.TraineeAllocationManager;
import com.sparta.skeleton.controller.trainee.TraineeGenerator;
import com.sparta.skeleton.controller.trainee.TraineeManager;
import com.sparta.skeleton.controller.trainingcentre.TrainingCentreGenerator;
import com.sparta.skeleton.controller.trainingcentre.TrainingCentreManager;
import com.sparta.skeleton.model.client.Client;
import com.sparta.skeleton.model.trainees.Trainee;
import com.sparta.skeleton.model.trainees.TraineeStage;
import com.sparta.skeleton.model.trainingCentres.TrainingCentre;
import com.sparta.skeleton.utilities.Configurator;
import com.sparta.skeleton.view.DisplayManager;

import java.util.*;
import java.util.stream.Collectors;

public class SimulationSystem {

    protected final Deque<Trainee> trainees = new LinkedList<>();

    protected final ArrayList<Client> clients = new ArrayList<>();

    protected final ArrayList<TrainingCentre> trainingCentres = new ArrayList<>();
    private int newClientFreq;
    private int newCentreFreq;

    public void runSimulation(int years, String outputFrequency) {

        Configurator.loadConfiguration(this);
        //JSONFileWriter.openJSONFile();
        int durationInMonths = years * 12;
        for (int currentMonth = 1; currentMonth <= durationInMonths; currentMonth++) {
            TraineeGenerator.generateTrainees(trainees); // Generate new trainees
            if (currentMonth % newCentreFreq == 0) { // Generate new centre every newCentreFreq months
                TrainingCentreGenerator.generateTrainingCentre(trainingCentres);
            }
            if (currentMonth % newClientFreq == 0) { // Generate new client every newClientFreq months
                clients.add(ClientGenerator.generateClient());
            }
            TraineeAllocationManager.allocate(trainees, trainingCentres);
            incrementMonthCounter();
            TrainingCentreManager.close(trainingCentres, trainees);
            TraineeAllocationManager.benchTrainees(trainees);
            TraineeAllocationManager.allocateToClients(trainees, clients);
            exportOutputToJSON(outputFrequency, durationInMonths, currentMonth);
        }
        //JSONFileWriter.closeJSONFile();
    }

    private void exportOutputToJSON(String outputFrequency, int durationInMonths, int i) {
        if (outputFrequency.equals("y") && i % 12 == 0) {
            DisplayManager.printOutput(this, i / 12, false);
            JSONFileWriter.exportToJSON(this, i /12,"y");
        } else if (outputFrequency.equals("m")) {
            DisplayManager.printOutput(this, i, true);
            JSONFileWriter.exportToJSON(this, i,"m");
        } else if (outputFrequency.equals("f") && i == durationInMonths) {
            DisplayManager.printOutput(this, i / 12, false);
            JSONFileWriter.exportToJSON(this, i /12,"f");
        }
    }

    private void incrementMonthCounter() {
        ClientManager.incrementClientMonth(clients);
        TrainingCentreManager.incrementMonthCounter(trainingCentres);
        TraineeManager.incrementTraineeMonthCounter(trainees);
    }

    public void setNewCentreFreq(int newCentreFreq) {
        this.newCentreFreq = newCentreFreq;
    }

    public void setNewClientFreq(int newClientFreq) {
        this.newClientFreq = newClientFreq;
    }

    public List<TrainingCentre> getListOfFullTrainingCentres() {
        return trainingCentres.stream().filter(TrainingCentre::trainingCentreIsFull).collect(Collectors.toList());
    }

    public List<Client> getListOfHappyClients() {
        return clients.stream().filter(Client::isHappy).collect(Collectors.toList());
    }

    public List<Client> getListOfUnhappyClients() {
        return clients.stream().filter(client -> !client.isHappy()).collect(Collectors.toList());
    }
    public long getNumberOfTrainingCentresByType(List<TrainingCentre> trainingCentres, String typeOfTrainingCentres) {
        return trainingCentres.stream().filter(trainingCentre -> trainingCentre.getClass().getSimpleName().equals(typeOfTrainingCentres)).count();
    }

    public long getNumberOfClientsByType(List<Client> clients, String traineeType) {
        return clients.stream().filter(client -> Arrays.asList(client.getRequiredTraineeType()).contains(traineeType)).count();
    }

    public long getNumberOfTrainees(TraineeStage stage) {
        return trainees.stream().filter(trainee -> trainee.getCurrentStage() == stage).count();
    }

    public long getNumberOfTrainees(TraineeStage stage, String courseType) {
        return trainees.stream().
                filter(trainee -> trainee.getCurrentStage() == stage
                && trainee.getCourseType().equals(courseType)).count();
    }

    @Override
    public String toString() {
        return SimulationOutput.getSimulationOutput(this);
    }

}
