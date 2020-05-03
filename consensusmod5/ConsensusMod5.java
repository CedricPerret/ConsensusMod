package consensusmod5;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

public class ConsensusMod5 {


    public static void main(String[] args) {

        int nEvent;double sdX;int speaker;int listener;
        
        //Parameters
        int nIndIni;
        int Nsimul;
        int limListener;
        double xThr;
        double kAlpha;
        
        //Leader profile
        double leadAlpha;
        //Follower profile
        double follAlpha;
        String wd = System.getProperty("user.dir")+"/";
        String nameFile = "";
        PrintWriter pw = null;
        String resTemp = "";
        boolean detail;
        
        long toComplete;
       //Profile

       System.out.println(wd);
      ExecutorService pool = Executors.newFixedThreadPool(5);
      
      nameFile = wd + "ConsensusMod7Data_" + 
              "N500_fThr0.05_nL30_diffAlpha0.7" + 
              ".txt";
      
      try {pw = new PrintWriter(new FileWriter(nameFile));} 
      catch (IOException ex) {System.out.println(ex);}
      resTemp = "";


      	for(int pNIndIni= 500; pNIndIni <= 500; pNIndIni+=500){
      		pool.execute(new Thread(new model1(nIndIni = pNIndIni, Nsimul = 1, limListener = 30, xThr = 0.05, 
      				kAlpha = 4,
      				leadAlpha = 0.85, follAlpha = 0.15,
      				pw = pw, detail = false)));
      }
      
      toComplete=((ThreadPoolExecutor) pool).getTaskCount()-((ThreadPoolExecutor) pool).getCompletedTaskCount();
      while(toComplete>0) {
      	toComplete = ((ThreadPoolExecutor) pool).getTaskCount()-((ThreadPoolExecutor) pool).getCompletedTaskCount();
      	System.out.println("Pool size is now " + toComplete);
      	try{Thread.sleep(30000);}catch(InterruptedException ex){}
      }
      
      pool.shutdown();
      try {
		pool.awaitTermination(600, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}

        
    }
    
}
