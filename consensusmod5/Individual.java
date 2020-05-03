package consensusmod5;
import java.util.Comparator;

public class Individual implements Comparable<Individual> {
    // Influence
    private double alpha;
    // Opinion
    private double x;
    
    
    public static int nbreIndividual = 0;
    public static int getNbreIndividual(){
        return nbreIndividual;
    }
    
    public Individual(){
        System.out.println("Creation of a default individual");
        alpha = 0;
        x = 0;
        nbreIndividual++;
    }
    
    public Individual(double pAlpha, double pX){
        alpha = pAlpha;
        x = pX; 
        nbreIndividual++;
    }
    
    public Individual(Individual pIndividual){
        alpha = pIndividual.alpha;
        x = pIndividual.x;
        nbreIndividual++;
    }
    
    public void setAlpha(double pAlpha){
        alpha = pAlpha;
    }
    public void setX(double pX){
        x = pX;
    }

    public double getAlpha(){
        return alpha;
    }
    public double getX(){
        return x;
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


