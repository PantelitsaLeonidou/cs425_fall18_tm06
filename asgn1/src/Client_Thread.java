package asgn1;

import java.net.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * This program demonstrates a simple TCP/IP socket client that reads input from
 * the user and prints echoed message from the server.
 */

public class Client_Thread extends Thread {
	String hostname;
	int port;
	String IPad;
	int userid;
	long myaverage;
	int iterations;

	public static final String LATENCY = "latency_8user_2cpu";
	// PrintWriter file_wr;

	public Client_Thread(String hostname, int port, String IPad, int userid, int iterations)
			throws FileNotFoundException {
		this.hostname = hostname;
		this.port = port;
		this.IPad = IPad;
		this.userid = userid;
		this.iterations = iterations;

	}

	public void run() {

		int c = 0;

		try (Socket socket = new Socket(hostname, port)) {

			long average_sum = 0;
			long average = 0;
			while (c < 300) {
				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, true);

				long rtt_sub = 0;
				//write counter of request
				writer.println(c);
				//write iteration number that client thread is created
				writer.println(iterations);
				
				// Request Info send to server
				long time_send = System.currentTimeMillis();
				writer.println( "Hello Message from" + "	User:" + userid + "	of	Ip:" + IPad);
				
				DataInputStream dIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				
				//Read Response of Server
				
								
				System.out.println(dIn.readUTF());
				int length = dIn.readInt();
				//read payload
				if (length > 0) {
					byte[] message = new byte[length];
					dIn.readFully(message, 0, message.length); // read the message
				}

				long time_recieve = System.currentTimeMillis();

				rtt_sub = time_recieve - time_send;
				average_sum += rtt_sub;

				c++;
			}
			
			average = average_sum / 300;

			this.myaverage = average;
			
			//write average to file
			
			File file = new File(LATENCY + iterations);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);

			BufferedReader br = new BufferedReader(new FileReader(file)); // each thread reads a different input file
			String line = "";

			while ((line = br.readLine()) != null) {
			}
			synchronized (this) {
				bw.append("user:" + userid + " " + average); // write to file
				bw.newLine();
			}
			br.close();
			bw.close();
			socket.close();

			// file_wr.close();

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());
		}

	}
}
