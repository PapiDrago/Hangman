package net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HangmanClient extends Thread {
	

	public static void main(String[] args) {
		String indirizzoIp = "192.168.1.67";
		int port = 770;
		try {
			Socket clientSocket = new Socket(indirizzoIp, port);
			RemoteClient client = new RemoteClient(clientSocket);
			client.start();
			client.scrivi();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
