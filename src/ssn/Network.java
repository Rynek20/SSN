
package ssn;

import neuron.Neuron;


public class Network {
    private int NumberOfLayers;
    private int[] NeuronsInLayer;
    
    private Neuron[][] networkStructure;
    
    public Network(int NumberOfLayers, int[] NeuronsInLayer){
        if(NeuronsInLayer.length != NumberOfLayers)
            throw new IllegalArgumentException();
        
        this.NumberOfLayers = NumberOfLayers;
        this.NeuronsInLayer = NeuronsInLayer;
        
        networkStructure = new Neuron[NumberOfLayers][0];
        for(int i=0;i<NumberOfLayers;i++){
            networkStructure[i] = new Neuron[NeuronsInLayer[i]];
        }
        
        for(int i=0; i<this.NumberOfLayers;i++){
            for(int j=0;j<this.NeuronsInLayer[i];j++){
                int inputs=1;
                if(i>0) inputs = this.NeuronsInLayer[i-1];
                networkStructure[i][j] = new Neuron(inputs);
            }
        }
    }
}
