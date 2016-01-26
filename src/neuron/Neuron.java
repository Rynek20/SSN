
package neuron;

import java.util.HashMap;
import java.util.Map;


public class Neuron extends Thread {

    private Map<String, Double> weights;
    private Map<String, Double> inputs;
    private double output;
    private int inputsAmount;
    private int outputsAmount;

    public Neuron(int inputsAmount) {
        inputs = new HashMap<>();
        output = 0;
        
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
    
    @Override
    public void run(){
        double sum = getSum();
        output = activationFunction(sum);
    }
    
    public boolean calculateNeuron(){
        if (inputsAmount<1) return false;
        this.start();
        return true;
    }
    
    private double getSum() {
        double sum = 0;
        if (inputsAmount > 0) {
            for (int i = 0; i < inputsAmount; i++) {
                sum += inputs.get("x"+i) * weights.get("w"+i);
            }
        }
        return sum;
    }
    
    private double activationFunction(double x){
        double y = 1 / (1+ Math.pow(Math.E, -x) );
        return y;
    }

    public boolean setInputSignal(double[] signals) {
        if (signals.length != inputsAmount) {
            return false;
        }

        for (int i = 0; i < signals.length; i++) {
            inputs.put("x" + i, signals[i]);
        }
        return true;
    }

}
