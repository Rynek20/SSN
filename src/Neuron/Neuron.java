/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neuron;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adm
 */
public class Neuron {

    private Map<String, Double> weights;
    private Map<String, Double> inputs;
    private int inputsAmount;
    private int outputsAmount;

    public void Neuron(int inputsAmount, int outputsAmount) {
        inputs = new HashMap<>();

        if (inputsAmount > 0) {
            this.inputsAmount = inputsAmount;
        } else {
            this.inputsAmount = 3;
        }

        if (outputsAmount > 0) {
            this.outputsAmount = outputsAmount;
        } else {
            this.outputsAmount = 1;
        }

        weights = new HashMap<>();
        for (int i = 0; i < inputsAmount; i++) {
            double d = (Math.random() * 2) - 1;
            if (d == 0) {
                d = 0.01;
            }

            weights.put("w" + i, d);
        }

    }

    private double sum() {
        double sum = 0;
        if (inputsAmount > 0) {
            for (int i = 0; i < inputsAmount; i++) {
                sum += inputs.get("x"+i) * weights.get("w"+i);
            }
        }
        return sum;
    }
    
    private double activationFunction(double x){
        
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
