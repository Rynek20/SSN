package neuron;

import java.util.HashMap;
import java.util.Map;

public class Neuron extends Thread {

    private Map<String, Double> weights;
    private Map<String, Double> inputs;
    private Double output;
    private int inputsAmount;
    private int outputsAmount;

    public Neuron(int inputsAmount) {
        inputs = new HashMap<>();
        output = new Double(0);

        if (inputsAmount > 0) {
            this.inputsAmount = inputsAmount;
        } else {
            this.inputsAmount = 3;
        }

        this.outputsAmount = 1;

        weights = new HashMap<>();
        for (int i = 0; i < inputsAmount; i++) {
            double d = (Math.random() * 2) - 1;
            if (d == 0) {
                d = 0.01;
            }

            weights.put("w" + i, d);
        }

    }
    public void changeOutput(){
        output = (Double)10.0;
    }
    @Override
    public void run() {
        double sum = getSum();
        output = activationFunction(sum);
    }

    public boolean calculateNeuron() {
        if (inputsAmount < 1) {
            return false;
        }
        this.start();
        return true;
    }

    private double getSum() {
        double sum = 0;
        if (inputsAmount > 0) {
            for (int i = 0; i < inputsAmount; i++) {
                sum += inputs.get("x" + i) * weights.get("w" + i);
            }
        }
        return sum;
    }

    private double activationFunction(double x) {
        double y = 1 / (1 + Math.pow(Math.E, -x));
        return y;
    }

    public boolean setInputs(double[] signals) {
        if (signals.length != inputsAmount) {
            return false;
        }
        if (!inputs.isEmpty()) {
            inputs.clear();
        }
        for (int i = 0; i < signals.length; i++) {
            inputs.put("x" + i, signals[i]);
        }
        return true;
    }

    public boolean setInput(double input) {
        if (inputsAmount != 1) {
            return false;
        }
        if (!inputs.isEmpty()) {
            inputs.clear();
        }
        inputs.put("x0", input);
        return true;
    }
    
    public boolean setInputs(Neuron[] neuronInputs){
        if (neuronInputs.length != inputsAmount) {
            return false;
        }
        if (!inputs.isEmpty()) {
            inputs.clear();
        }

        for (int i=0;i<neuronInputs.length;i++) {
            inputs.put("x" + i, neuronInputs[i].getOutput());
        }
        return true;
    }
    
    public Double getOutput(){
        return output;
    }

}
