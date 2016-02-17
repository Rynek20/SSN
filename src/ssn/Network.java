package ssn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network extends Thread {

    private int NumberOfLayers;
    private int[] NeuronsInLayer;
    private Neuron[][] networkStructure;
    private double trainingTime;
    private ArrayList<DataVector> trainingData;
    private ArrayList<DataVector> validationData;
    private double networkError;
    private double learningRate = 0.01;

    public Network(int NumberOfLayers, int[] NeuronsInLayer) {
        if (NeuronsInLayer.length != NumberOfLayers) {
            throw new IllegalArgumentException();
        }

        this.NumberOfLayers = NumberOfLayers;
        this.NeuronsInLayer = NeuronsInLayer;
        this.trainingTime = -1;
        this.trainingData = new ArrayList<>();
        this.validationData = new ArrayList<>();
        this.networkError = 0;

        networkStructure = new Neuron[NumberOfLayers][0];
        for (int i = 0; i < NumberOfLayers; i++) {
            networkStructure[i] = new Neuron[NeuronsInLayer[i]];
        }

        for (int i = 0; i < this.NumberOfLayers; i++) {
            for (int j = 0; j < this.NeuronsInLayer[i]; j++) {
                int inputs = 1;
                if (i > 0) {
                    inputs = this.NeuronsInLayer[i - 1];
                }
                networkStructure[i][j] = new Neuron(inputs);
            }
        }
        for (int i = 1; i < this.NumberOfLayers; i++) {
            for (int j = 0; j < this.NeuronsInLayer[i]; j++) {
                networkStructure[i][j].setInputs(networkStructure[i - 1]);
            }
        }
    }

    public void setTrainingData(DataVector[] data) {
        this.trainingData.addAll(Arrays.asList(data));
    }

    public void setValidationData(DataVector[] data) {
        this.validationData.addAll(Arrays.asList(data));
    }

    public void startTraining() {

        trainingData.stream().forEach((dataVector) -> {
            for (int n = 0; n < NumberOfLayers; n++) {
                Semaphore sem = new Semaphore(-NeuronsInLayer[n] + 1);
                for (int i = 0; i < NeuronsInLayer[n]; i++) {
                    if (n == 0) {
                        networkStructure[n][i].setInput(dataVector.getInputParameter(i));
                    }
                    networkStructure[n][i].calculateNeuron(sem);
                }
                try {
                    sem.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Przechodze do kolejnej warstwy");
            }
            for (int i = 0; i < NeuronsInLayer[NumberOfLayers - 1]; i++) {
                double answer = networkStructure[NumberOfLayers - 1][i].getOutputValue();
                double expectedValue = dataVector.getOutputParameter(i);
                double derivative = networkStructure[NumberOfLayers - 1][i].derivativeActivationFunction();
                networkStructure[NumberOfLayers - 1][i].setNeuronError(derivative*(expectedValue - answer));
            }
            for (int i = NumberOfLayers - 2; i >= 0; i--) {
                for (int j = 0; j < NeuronsInLayer[i]; j++) {
                    double error = 0;
                    double derivative = networkStructure[i][j].derivativeActivationFunction();
                    for (int k = 0; k < NeuronsInLayer[i + 1]; k++) {
                        error += networkStructure[i + 1][k].getOutputValue() * networkStructure[i + 1][k].getWeight(j);
                    }
                    networkStructure[i][j].setNeuronError(derivative * error);
                }
            }

            for (int i = 0; i < this.NumberOfLayers; i++) {
                for (int j = 0; j < this.NeuronsInLayer[i]; j++) {
                    int inputsCount = networkStructure[i][j].getInputsAmount();
                    for(int k=0;k<inputsCount;k++){
                        double newWeight = learningRate * networkStructure[i][j].getNeuronError() * networkStructure[i][j].getInputValue(k) ; 
                        networkStructure[i][j].setWeight(k, newWeight);
                    }
                }
            }

            //po zakończeniu obliczeń uruchomienie  wstecznej propagacji błędów
            //http://www.ai.c-labtech.net/sn/pod_prakt.html
            //http://edu.pjwstk.edu.pl/wyklady/nai/scb/wyklad3/w3.htm
            // http://home.agh.edu.pl/~vlsi/AI/backp_t/backprop.html
        });

    }
    
    public double answer(DefaultDataVector data){
        for (int n = 0; n < NumberOfLayers; n++) {
                Semaphore sem = new Semaphore(-NeuronsInLayer[n] + 1);
                for (int i = 0; i < NeuronsInLayer[n]; i++) {
                    if (n == 0) {
                        networkStructure[n][i].setInput(data.getInputParameter(i));
                    }
                    networkStructure[n][i].calculateNeuron(sem);
                }
                try {
                    sem.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Przechodze do kolejnej warstwy");
            }
        return networkStructure[NumberOfLayers-1][0].getOutputValue();
    }
}
