package ssn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network extends Thread {

    private int NumberOfLayers;
    private int[] NeuronsInLayer;
    private Neuron[][] networkStructure;
    private double trainingTime;
    private ArrayList<DataVector> trainingData;
    private ArrayList<DataVector> validationData;

    public Network(int NumberOfLayers, int[] NeuronsInLayer) {
        if (NeuronsInLayer.length != NumberOfLayers) {
            throw new IllegalArgumentException();
        }

        this.NumberOfLayers = NumberOfLayers;
        this.NeuronsInLayer = NeuronsInLayer;
        this.trainingTime = -1;
        this.trainingData = new ArrayList<>();
        this.validationData = new ArrayList<>();

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
                for (int i = 0; i < NeuronsInLayer[n]; i++) {
                    if(n==0) networkStructure[n][i].setInput(dataVector.getInputParameter(i));
                    networkStructure[n][i].calculateNeuron();
                }
                for (int i = 0; i < NeuronsInLayer[n]; i++) {
                    try {
                        networkStructure[n][i].join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
}
