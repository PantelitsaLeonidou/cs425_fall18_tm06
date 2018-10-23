package asgn1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

	
	 public static void main(String[] args) throws IOException {
		   if (args.length < 3) return;
		   
	        String hostname = args[0];
	        int port = Integer.parseInt(args[1]);
	        int iterations=Integer.parseInt(args[2]);
	      
	    	int userid=0;
	    	
	    	
	    	for(int i =0;i<iterations;i++) {
	    		
	    		 
	            while (userid<25) {
	            	InetAddress localHost = InetAddress.getLocalHost();
	    			String IPad = localHost.getHostAddress().trim();

	    		
	    			
	               Client_Thread mythread=new Client_Thread(hostname,port,IPad,userid,i);
	               mythread.start();
	              // writer.println((mythread.myaverage));
	                userid++;
	 
	               
	            }userid=0;
	            
	    	}
	            
	          
	    	
	            
	    	//}
	            //writer.close();
	            
	 
	         
	    }
}
