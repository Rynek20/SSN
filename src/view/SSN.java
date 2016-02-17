
package view;

import gui.MainWindow;
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
        DefaultDataVector[] data = new DefaultDataVector[10];
        data[0] = createVector(1, 2, 0);
        data[1] = createVector(2, 2, 0);
        data[2] = createVector(3, 2, 0);
        data[3] = createVector(5, 5, 1);
        data[4] = createVector(6, 7, 1);
        data[5] = createVector(4, 5, 1);
        data[6] = createVector(1, 1, 0);
        data[7] = createVector(2, 3, 0);
        data[8] = createVector(7, 4, 1);
        data[9] = createVector(8, 8, 1);
        
        MainWindow window = new MainWindow(data);
        window.setVisible(true);    
        
        siec.setTrainingData(data);
        siec.startTraining();
        
        DefaultDataVector d = new DefaultDataVector();
        d.setInputParameter(0, 7);
        d.setInputParameter(1, 5);
        
        System.out.println(siec.answer(d));
        System.out.println("koniec");
    }
    
    private static DefaultDataVector createVector(double x, double y , int value){
        DefaultDataVector d = new DefaultDataVector();
        d.setInputParameter(0, x);
        d.setInputParameter(1, y);
        d.setOutputParameter(0, value);
        return d;
    }
    
}
