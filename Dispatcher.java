package hw5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
//import com.oracle.common.base.Timeout;

public class Dispatcher extends Thread{
	
	//static Queue<Dispatcher> queue = new LinkedList<>();
	BlockingQueue<String> queue1;
	BlockingQueue<Holder> outputQueue;
	
	private static class Holder{
		int result;
		String hashValue;
		
		public Holder(String var, int result) {
			this.hashValue = var;
			this.result = result;
		}
		
	}


	
	int timeOut;
	
	public Dispatcher(BlockingQueue<String> queue1, BlockingQueue<Holder> outputQueue, int timeOut) {
		this.queue1 = queue1;
		this.outputQueue = outputQueue;
		this.timeOut = timeOut;
	}
	
	public void run() {
		UnHash unhashFunc = new UnHash();
		while(true) {
			try {
				final String var = queue1.take();
				Thread timeoutThread = new Thread() {
					public void run() {
						//outputQueue.add(unhashFunc.unhash(var));
						int result = unhashFunc.unhash(var);
						Holder res = new Holder(var, result);
						outputQueue.add(res);

					}
					
				};
				timeoutThread.start();
				Timer t = new Timer();
				if (timeOut != 0) {
					
				
					t.schedule(new TimerTask() {
						public void run() {
							timeoutThread.interrupt();
						}
					}, timeOut);
				}
				timeoutThread.join();
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		//UnHash unhashFunc = new UnHash();
		int hashCounter = 0;
		BlockingQueue<String> queue = new LinkedBlockingQueue<>();
		BlockingQueue<Holder> outputQueue = new LinkedBlockingQueue<>();

	    try {
	      File myObj = new File(args[0]);
	      int numberOfThreads = Integer.parseInt(args[1]);
	      int timeOut = 0;
	      if (args.length >= 3) {
		      timeOut = Integer.parseInt(args[2]);
	      }
	      
	      for (int i = 0; i < numberOfThreads; i++) {
	    	  Dispatcher object = new Dispatcher(queue, outputQueue, timeOut);
	    	  //object.setDaemon(true);
	    	  object.start();
	      }
	      
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	    	String data = myReader.nextLine();
	    	queue.add(data);
	    	hashCounter ++;
	      }
	      //System.out.println(hashCounter);
	      //if(2 > 1) {
		    //  return;
	      //}
	      for(int i = 0; i < hashCounter; i++) {
	    	  try {
	    		  Holder result = outputQueue.take();
	    		  if (result.result == -1) {
	  				System.out.println(result.hashValue);
	    		  }
	    		  else {
		  				System.out.println(result.result);

	    		  }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      
	      
	      myReader.close();
	      System.exit(0);
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	  }

}
