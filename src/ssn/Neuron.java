package ssn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Neuron extends Thread {

    private class DoubleHolder {

        Double value = 0.0;

        public DoubleHolder(double d) {
            value = d;
        }
    }
    private final Map<String, DoubleHolder> weights;
    private final Map<String, DoubleHolder> inputs;
    private DoubleHolder output;
    private final int inputsAmount;
    private final int outputsAmount;
    private Boolean suspended;
    private final Object holder;
    private Semaphore semaphore;
    private double neuronError;

    public Neuron(int inputsAmount) {
        inputs = new HashMap<>();
        output = new DoubleHolder(0);
        holder= new Object();
        neuronError=0;

        if (inputsAmount > 0) {
            this.inputsAmount = inputsAmount;
        } else {
            this.inputsAmount = 3;
        }

        this.outputsAmount = 1;
        this.suspended = false;

        weights = new HashMap<>();
        for (int i = 0; i < inputsAmount; i++) {
            double d = (Math.random() * 2) - 1;
            if (d == 0) {
                d = 0.001;
            }

            weights.put("w" + i, new DoubleHolder(d));
        }

    }

    @Override
    public void run() {
        while (true) {
            DoubleHolder sum = getSum();
            output = activationFunction(sum);
            System.out.println("neuron obliczyl");
            semaphore.release();
            try {
                suspended = true;
                synchronized (holder) {
                    holder.wait();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Neuron.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean calculateNeuron(Semaphore semaphore) {
        if (inputsAmount < 1) {
            return false;
        }
        this.semaphore = semaphore;
        if (suspended) {
            suspended = false;
            synchronized (holder) {
                holder.notifyAll();
            }
        } else {
            this.start();
        }
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
        double y = 1.0 / (1.0 + Math.pow(Math.E, -x.value));
        return new DoubleHolder(y);
    }
    
    public double derivativeActivationFunction(double x){
        DoubleHolder f = activationFunction(new DoubleHolder(x));
        return f.value*(1-f.value);
    }
    public double derivativeActivationFunction(){
        DoubleHolder f = activationFunction(new DoubleHolder(getSum().value));
        return f.value*(1-f.value);
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

    public boolean setInputs(Neuron[] neuronInputs) {
        if (neuronInputs.length != inputsAmount) {
            return false;
        }
        if (!inputs.isEmpty()) {
            inputs.clear();
        }

        for (int i = 0; i < neuronInputs.length; i++) {
            inputs.put("x" + i, neuronInputs[i].getOutput());
        }
        return true;
    }

    private DoubleHolder getOutput() {
        return output;
    }

    public double getOutputValue() {
        return output.value;
    }
    
    public double getNeuronError(){
        return neuronError;
    }

    public void setNeuronError(double neuronError) {
        this.neuronError = neuronError;
    }
    
    public double getWeight(int nr){
        return weights.get("w"+nr).value;
    }
    
    public boolean setWeight(int nr, double value){
        if(weights.containsKey("w"+nr)){
            weights.put("w"+nr, new DoubleHolder(value));
            return true;
        }
        return false;
    }

    public int getInputsAmount() {
        return inputsAmount;
    }
    
    public double getInputValue(int nr){
        return inputs.get("x" + nr).value;
    }
    
}
