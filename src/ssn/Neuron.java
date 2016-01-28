package ssn;

import java.util.HashMap;
import java.util.Map;

public class Neuron extends Thread {
    private class DoubleHolder{
        Double value = 0.0;
        public DoubleHolder(double d){
            value = d;
        }
    }
    private final Map<String, DoubleHolder> weights;
    private final Map<String, DoubleHolder> inputs;
    private DoubleHolder output;
    private final int inputsAmount;
    private final int outputsAmount;

    public Neuron(int inputsAmount) {
        inputs = new HashMap<>();
        output = new DoubleHolder(0);

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

            weights.put("w" + i, new DoubleHolder(d));
        }

    }

    @Override
    public void run() {
        DoubleHolder sum = getSum();
        output = activationFunction(sum);
    }

    public boolean calculateNeuron() {
        if (inputsAmount < 1) {
            return false;
        }
        this.start();
        return true;
    }

    private DoubleHolder getSum() {
        double sum = 0;
        if (inputsAmount > 0) {
            for (int i = 0; i < inputsAmount; i++) {
                sum += inputs.get("x" + i).value * weights.get("w" + i).value;
            }
        }
        return new DoubleHolder(sum);
    }

    private DoubleHolder activationFunction(DoubleHolder x) {
        double y = 1 / (1 + Math.pow(Math.E, -x.value));
        return new DoubleHolder(y);
    }

    public boolean setInputs(double[] signals) {
        if (signals.length != inputsAmount) {
            return false;
        }
        if (!inputs.isEmpty()) {
            inputs.clear();
        }
        for (int i = 0; i < signals.length; i++) {
            inputs.put("x" + i, new DoubleHolder(signals[i]));
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
        inputs.put("x0", new DoubleHolder(input));
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
    
    private DoubleHolder getOutput(){
        return output;
    }
    
    public double getOutputValue(){
        return output.value;
    }

}
