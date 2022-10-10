package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import console.LocalPlayer;
import hangman.Hangman;
import hangman.Player;

public class HangmanClient {
	public static void main(String[] args) {
		String indirizzoIp = "PapiDrago-hub";
		int port = 770;
		try {
			Socket clientSocket = new Socket(indirizzoIp, port);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
			String input = "";
			while(true) {
				input = console.readLine();
				System.out.println(input);
				outputStream.println(input);
				System.out.println(inputStream.readLine());
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
}
