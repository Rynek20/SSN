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
    private int inputs

    public void Neuron(int inputsNr, int outputNr) {
        weights = new HashMap<>();
        for (int i = 0; i < inputsNr; i++) {
            double d =  Math.random();
            if(d==0) d=0.1;
            
            weights.put("w"+i, d);
        }

    }
    
    public boolean setInputSignal(double[] signals){
        
    }

}
