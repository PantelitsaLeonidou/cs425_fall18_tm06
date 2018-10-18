package asgn1;

import java.net.*;
import java.util.Scanner;
import java.io.*;
 
/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 */
public class Client_Thread extends Thread {
	String hostname;
	int port;
	String IPad;
	int userid;
	
	   public Client_Thread(String hostname,int port,String IPad,int userid) {
		   this.hostname=hostname;
		   this.port=port;
		   this.IPad=IPad;
		   this.userid=userid;
		   
	   }
    public void run () {
     
        int c=0;
    
        try (Socket socket = new Socket(hostname, port)) {
        	while(c<300) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            
      
            
            	String str="Hello";
            	 writer.println(c);
                writer.println(str);
                writer.println(IPad);
                writer.println(userid);
               
                
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
                String time = reader.readLine();
 
                System.out.println(time);
             
        
         c++;
        	 }
        	
        	 socket.close();
            
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
   
    }
    }
