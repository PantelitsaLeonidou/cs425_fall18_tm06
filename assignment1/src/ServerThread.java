package asgn1;

import java.io.*;
import java.net.*;
 
/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
            int c=0;
            
           
            do {
            	 c=Integer.parseInt(reader.readLine()); 
               String hello = reader.readLine();
                System.out.println(hello);
               String IP = reader.readLine();
                System.out.println(IP);
                String userid = reader.readLine();
                System.out.println(userid);
                writer.println("Welcome user:"+ userid);
               int payload= (int) (((Math.random() * ((2000 - 300) + 1)) + 300)*1024);
               writer.println("payload: "+ payload);
                
               
            } while (c<299);
 
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}