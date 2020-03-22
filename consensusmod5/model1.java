package consensusmod5;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

public class model1 extends Thread {
    private int nIndIni;
    private int Nsimul; 
    private int limListener; 
    private double fThr; 
    private double leadAlpha; 
    private double follAlpha; 
    private PrintWriter pw;
    private boolean detail;
    
    public model1(int nIndIni,
     int Nsimul, 
     int limListener, 
     double fThr, 
     double leadAlpha, 
     double follAlpha, 
     PrintWriter pw,
     boolean detail){
        this.nIndIni = nIndIni;
        this.Nsimul = Nsimul;
        this.limListener = limListener;
        this.fThr = fThr;
        this.leadAlpha = leadAlpha;
        this.follAlpha = follAlpha;
        this.pw = pw;
        this.detail = detail;
    }
    
    
    public void run(){
        
        double fVar; double fVarLead; int nEvent; int speaker; int limListener2 = limListener; String resTemp ="";
        double fMax = 1;
        double alphaSum; 
        double diffAlpha; double newFSpeaker;                                                       //CHANGE
        int[] listenerList;
        int nLead;
        if(nIndIni <= limListener){limListener2 = nIndIni-1;}                   //If there are less people than the limit of listener, speaker talks to all
        //else{limListener2 = Math.round((limListener*nIndIni)/100);}           //If limListener is a percentage
        
        //Id We do the simulation for each possible composition of the population
        	//int[] nLeadList = new int[nIndIni];
        	//for(int i=0;i<nLeadList.length;i++) {nLeadList[i]=i;}
        //For specific list of nLead
        int[] nLeadList = {0,1,2,10};
        
        for(int count = 0; count<nLeadList.length;count++){				
        	nLead=nLeadList[count];
        	
            resTemp = "";
            for(int iSimul = 0; iSimul < Nsimul; iSimul++){
                //Initialisation
                List<Individual> popNow = new ArrayList<>();
                for(int i = 0; i < nLead; i++){                                 //Number of leaders
                	//With random opinion
                	if(detail == false) {
                		popNow.add(new Individual(leadAlpha, Math.random()*fMax));
                	}
                    //For fixed leader opinion (the most spread out possible)
                	if(detail == true) {
                		double x_lead_init = ((double)i +1)/((double)nLead+1);
                		popNow.add(new Individual(leadAlpha, x_lead_init));
                	}
                }
                fVarLead = Utility.variancePref(popNow);
                for(int i = nLead; i < nIndIni; i++){                           //Number of follower
                    popNow.add(new Individual(follAlpha, Math.random()*fMax));
                }

                fVar = Utility.variancePref(popNow);                                    //Initial variance of f
                nEvent = -1;
                
                alphaSum = 0.0d;                                                 // Sum of the alpha to calculate the probability of being chosen as a speaker
                double[] probSpeaker = new double[nIndIni];                     //Array of the probability of each individual
                for(int i=0; i<nIndIni; i++){                       
                    alphaSum += Math.pow(popNow.get(i).getAlpha(),4);
                }
                double probTemp = 0;                
                for(int i=0; i<nIndIni; i++){                                   //We calculate the weigthed probabilities
                    probSpeaker[i]= probTemp + (Math.pow(popNow.get(i).getAlpha(),4)/alphaSum);
                    probTemp = probSpeaker[i];
                }                                                   
                //Simulation of consensus decision making
                while(fVar > fThr){
                    if(nEvent != -1){
                        speaker = Utility.probSample(probSpeaker, Math.random());         //Sample a speaker as a function of alpha
                        if(nIndIni <= limListener2){limListener2 = nIndIni-1;}  
                        //speaker = (int)(Math.floor(Math.random()*(popNow.size())));       //Sample a random speaker
                        listenerList = Utility.randomSampleOtherList(nIndIni, limListener2,speaker);              //Create the list of listener (random individuals except speaker)
                        for(int i=0; i<limListener2; i++){
                            //ConsensusMod1 Only comparison
                            diffAlpha =  popNow.get(speaker).getAlpha() - popNow.get(listenerList[i]).getAlpha();
                            if(diffAlpha <= 0){diffAlpha = 0.01;}
                            popNow.get(listenerList[i]).setF(popNow.get(listenerList[i]).getF() + diffAlpha * (popNow.get(speaker).getF()-popNow.get(listenerList[i]).getF()));
                            
                        }
                        fVar = Utility.variancePref(popNow);
                    }
                    //Counter of time of consensus
                    nEvent++;
                    if(detail == true){
                        for(int k=0; k<nIndIni;k++){
                        resTemp += nEvent
                        		+","
                                + nIndIni
                                +","
                                + nLead
                                +","
                                + popNow.get(k).getF()
                            	+","
                            	+ popNow.get(k).getAlpha()
                            	+","
                            	+ k
                            	+","
                            	+ fVar        
                            	+","
                            	+ iSimul  
                            	+ "\r\n";
                    pw.write(resTemp); resTemp = "";
                        }
                        pw.flush();
                    }
                }
                //Writing
                if(detail == false){
                    resTemp += nEvent
                            +","
                            + nIndIni
                            +","
                            + nLead
                            + ","
                            + (nIndIni-nLead)
                            +","
                            + limListener
                            +","
                            + fVarLead
                            +","        
                            + fThr
                            +","        
                            + leadAlpha
                            +","        
                            + follAlpha
                            +","
                            + iSimul  
                            + "\r\n";
                    pw.write(resTemp); resTemp = "";
                }
                pw.flush();
            }

        }
    } 
}
