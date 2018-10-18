package asgn1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	 public static void main(String[] args) {
		   if (args.length < 2) return;
		   
	        String hostname = args[0];
	        int port = Integer.parseInt(args[1]);
	        String IPad="192.32.32.4:80";
	    	int userid=0;
	    	
	            while (userid<10) {
	                
	                new Client_Thread(hostname,port,IPad,userid).start();
	                userid++;
	 
	               
	            }
	 
	        
	    }
}
