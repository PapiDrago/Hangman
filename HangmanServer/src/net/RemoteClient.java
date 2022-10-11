package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RemoteClient extends Thread {
	private Socket clientSocket;
	private BufferedReader inputStream;
	private BufferedReader console;
	private PrintWriter outputStream;
	
	public RemoteClient(Socket socket) throws IOException {
		this.clientSocket = socket;
		this.inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.console = new BufferedReader(new InputStreamReader(System.in));
		this.outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
		
	}

	@Override
	public void run() {
		super.run();
		
		try {
			String inputMsg = "";
			while((inputMsg = inputStream.readLine()) != null) {
				System.out.println(inputMsg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void scrivi() {
		String outputMsg = "";
		while(true) {
			try {
				outputMsg = console.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputStream.println(outputMsg);
		}
	}
}
