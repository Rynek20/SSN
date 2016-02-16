
package view;

import ssn.DefaultDataVector;
import ssn.Network;

/**
 *
 * @author adm
 */
public class SSN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] tab = {2,5,1};
        Network siec = new Network(3, tab);
        DefaultDataVector[] data = new DefaultDataVector[2];
        data[0] = new DefaultDataVector();
        data[1] = new DefaultDataVector();
        data[0].setInputParameter(0, 1.34);
        data[0].setInputParameter(1, 2.33);
        data[0].setInputParameter(2, 1.2);
        data[0].setOutputParameter(0, 1);
        data[0].setOutputParameter(1, 1);
        data[1].setInputParameter(0, 0.34);
        data[1].setInputParameter(1, 1.33);
        data[1].setInputParameter(2, 3.2);
        data[1].setOutputParameter(0, 0);
        data[1].setOutputParameter(1, 0);
        siec.setTrainingData(data);
        siec.startTraining();
        System.out.println("koniec");
    }
    
}
