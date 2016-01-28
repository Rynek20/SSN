
package ssn;


public class Network extends Thread{
    private int NumberOfLayers;
    private int[] NeuronsInLayer;
    private Neuron[][] networkStructure;
    private double trainingTime;
    
    public Network(int NumberOfLayers, int[] NeuronsInLayer){
        if(NeuronsInLayer.length != NumberOfLayers)
            throw new IllegalArgumentException();
        
        this.NumberOfLayers = NumberOfLayers;
        this.NeuronsInLayer = NeuronsInLayer;
        this.trainingTime = -1;
        
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
        for(int i=1; i<this.NumberOfLayers;i++){
            for(int j=0;j<this.NeuronsInLayer[i];j++){
                networkStructure[i][j].setInputs(networkStructure[i-1]);
            }
        }
    }
    
    public void setTrainData(){
        
    }
    
    public void setValidationData(){
        
    }
    
    public void startTraining(){
        
    }
}
