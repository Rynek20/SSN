package ssn;

/**
 *
 * @author Rynek
 */
public class DefaultDataVector implements DataVector {

    private double[] inputParameters = new double[1];
    private double[] outpusParameters = new double[1];

    @Override
    public double getInputParameter(int nr) {
        if (inputParameters.length <= nr) {
            throw new IllegalArgumentException();
        }
        return inputParameters[nr];
    }

    @Override
    public void setInputParameter(int nr, double value) {
        if (inputParameters.length <= nr) {
            double[] tmp = new double[nr+1];
            for(int i=0;i<nr+1;i++){
                if(i==nr) tmp[i]=value;
                else tmp[i]=inputParameters[i];
            }
            inputParameters = tmp;
        }
        inputParameters[nr] = value;
    }

    @Override
    public double getOutputParameter(int nr) {
        if (outpusParameters.length <= nr) {
            throw new IllegalArgumentException();
        }
        return outpusParameters[nr];
    }

    @Override
    public void setOutputParameter(int nr, double value) {
        if (outpusParameters.length <= nr) {
            double[] tmp = new double[nr+1];
            for(int i=0;i<nr+1;i++){
                if(i==nr) tmp[i]=value;
                else tmp[i]=outpusParameters[i];
            }
            outpusParameters = tmp;
        }
        outpusParameters[nr] = value;
    }

    @Override
    public int inputsAmount() {
        return inputParameters.length;
    }

    @Override
    public int outputsAmount() {
        return outpusParameters.length;
    }

}
