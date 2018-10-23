package asgn1;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Attribute;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
	public static final String RESULTS = "results";
	private Socket socket;
	static long memory_before;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public static double getMemoryUsageUtilization() {

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - memory_before;
		double persantage = (100.0 * actualMemUsed) / (Runtime.getRuntime().totalMemory() * 1.0);
		return ((int) (persantage * 10) / 10.0);
	}

	public static double getProcessCpuLoad() throws Exception {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
		AttributeList list = mbs.getAttributes(name, new String[] { "ProcessCpuLoad" });

		if (list.isEmpty())
			return Double.NaN;

		Attribute att = (Attribute) list.get(0);
		Double value = (Double) att.getValue();

		if (value == -1.0)
			return Double.NaN;
		return ((int) (value * 1000) / 10.0);
	}

	static int count;

	synchronized public static void toFile(int count, final int iteration) throws Exception {
		File file = new File(RESULTS + iteration);
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);

		BufferedReader br = new BufferedReader(new FileReader(file)); // each thread reads a different input file
		String line = "";

		while ((line = br.readLine()) != null) {
		}

		bw.append(Integer.toString(count) + " " + getProcessCpuLoad() + " " + getMemoryUsageUtilization()); // write to
																											// file
		bw.newLine();

		br.close();
		bw.close();
	}

	synchronized static void startTimer(final int iteration) {

		count++;
		if (count == 1) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						toFile(count, iteration);
						count = 0;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			

			int c = 0;

			do {
				c = Integer.parseInt(reader.readLine());
				int iteration = Integer.parseInt(reader.readLine());
				startTimer(iteration);
				String req = reader.readLine();
				System.out.println(req);

				String[] sp = req.split("	");
				String userid = sp[1];
				String userIp=sp[3];

				// calculate payload size randomly
				Random num = new Random(System.nanoTime());
				int payload = (int) ((num.nextInt(((2000 - 300) + 1)) + 300) * 1024);
				// create a byte array of payload size and fill it with random values
				byte[] byte_array = new byte[payload];
				new Random().nextBytes(byte_array);
				
				//write response message to client
				DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
				dOut.writeUTF("Welcome " + userid+ " "+userIp);
				dOut.writeInt(byte_array.length); // write length of the message
				dOut.write(byte_array);

			} while (c < 299);

			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}