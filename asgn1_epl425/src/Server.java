package asgn1;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form. This server is multi-threaded.
 */
public class Server {

	public static void main(String[] args) {
		if (args.length < 1)
			return;

		int port = Integer.parseInt(args[0]);

		try (ServerSocket serverSocket = new ServerSocket(port)) {

			// System.out.println("Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				// System.out.println("New client connected");

				new ServerThread(socket).start();

			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}