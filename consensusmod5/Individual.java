package consensusmod5;
import java.util.Comparator;

public class Individual implements Comparable<Individual> {
    // Influence
    private double alpha;
    // Opinion
    private double f;
    
    
    public static int nbreIndividual = 0;
    public static int getNbreIndividual(){
        return nbreIndividual;
    }
    
    public Individual(){
        System.out.println("Creation of a default individual");
        alpha = 0;
        f = 0;
        nbreIndividual++;
    }
    
    public Individual(double pAlpha, double pF){
        alpha = pAlpha;
        f = pF; 
        nbreIndividual++;
    }
    
    public Individual(Individual pIndividual){
        alpha = pIndividual.alpha;
        f = pIndividual.f;
        nbreIndividual++;
    }
    
    public void setAlpha(double pAlpha){
        alpha = pAlpha;
    }
    public void setF(double pF){
        f = pF;
    }

    public double getAlpha(){
        return alpha;
    }
    public double getF(){
        return f;
    }

    public String Description(){
        return " \nalpha = " + alpha + "\nw = " + w + "\nf = " + f + "\nz = " + z + "\nm = " + m;
    }
    
    @Override
    public int compareTo(Individual obj)
    {
        // compareTo returns a negative number if this is less than obj, 
        // a positive number if this is greater than obj, 
        // and 0 if they are equal.
        if(this.alpha < obj.alpha)
          return 1;
        else if(obj.alpha < this.alpha)
          return -1;
          return 0;
    }
    

	
    
   
}


