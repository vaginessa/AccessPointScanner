package org.mtrec.assoc;

import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class MultiThreadServer extends Thread {
	private ServerSocket serverSocket;
	static int port = 8589;

	public MultiThreadServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(1000000);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("1");
				Socket socket = serverSocket.accept();
				System.out.println("2");
				
				
				Scanner networkInput = new Scanner(socket.getInputStream());

				PrintWriter networkOutput = new PrintWriter(
						socket.getOutputStream(), true);

				String tmp = networkInput.nextLine();
				System.out.println(tmp);

				String[] apList = tmp.split("\\|");

				for (int k = 0; k < apList.length; k++) {
					System.out.println(apList[k]);
					String[] p = apList[k].split("_");

					AP ap = new AP(p[0], p[1], p[2], p[3]);
					databaseUtil.insert(ap);

				}
				databaseUtil.query();

				networkOutput.println("HIHIHI");

				socket.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		try {
			Thread t = new MultiThreadServer(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}