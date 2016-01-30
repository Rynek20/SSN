
package ssn;


public interface DataVector {
    public double getParameter(int nr);
    public void setParameter(int nr, double value);
    public double getClassParameter();
    public void setClassParameter(double value);    
}
