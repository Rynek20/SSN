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
            for (int iteration = 0; iteration < 5; iteration++) {
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
                //obliczanie błędu dla warstwy wyjściowej
                for (int i = 0; i < NeuronsInLayer[NumberOfLayers - 1]; i++) {
                    double answer = networkStructure[NumberOfLayers - 1][i].getOutputValue();
                    double expectedValue = dataVector.getOutputParameter(i);
                    double derivative = networkStructure[NumberOfLayers - 1][i].derivativeActivationFunction();
                    double error = derivative * (expectedValue - answer);
                    networkStructure[NumberOfLayers - 1][i].setNeuronError(error);
                }
                
                //obliczanie błędów dla pozostałych warstw
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
                System.out.println("Obliczono błędy");
                
                //zmiana wag neuronów
                for (int i = 0; i < this.NumberOfLayers; i++) {
                    for (int j = 0; j < this.NeuronsInLayer[i]; j++) {
                        int inputsCount = networkStructure[i][j].getInputsAmount();
                        for (int k = 0; k < inputsCount; k++) {
                            double newWeight = learningRate * networkStructure[i][j].getNeuronError() * networkStructure[i][j].getInputValue(k);
                            networkStructure[i][j].setWeight(k, newWeight);
                        }
                    }
                }
                System.out.println("Zmieniono wagi");
            }
        });

    }

    public double answer(DefaultDataVector data) {
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
        }
        return networkStructure[NumberOfLayers - 1][0].getOutputValue();
    }
}
