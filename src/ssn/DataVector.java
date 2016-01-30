
package ssn;


public interface DataVector {
    public double getInputParameter(int nr);
    public void setInputParameter(int nr, double value);
    public double getOutputParameter(int nr);
    public void setOutputParameter(int nr, double value);   
    public int inputsAmount();
    public int outputsAmount();
}
