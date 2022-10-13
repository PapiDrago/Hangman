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
		String outputMsg = "";
		try {
			while((outputMsg = console.readLine()) != null) {
				outputStream.println(outputMsg);
			}
			System.out.println("eun");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void chiusuraConnessione() {
		try {
			this.inputStream.close();
			//this.console.close();
			this.console = null;
			this.outputStream.close();
			this.clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ascolta() throws IOException {
		try {
			String inputMsg = "";
			while((inputMsg = inputStream.readLine()) != null) {
				System.out.println(inputMsg);
			}
			chiusuraConnessione();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Quando il server chiude il suo endpoint (socket), viene "trasmesso"
	 * un NULL che mi permette di uscire dall'ascolto e di interrompere
	 * il thread che si occupa della trasmissione tramite tastiera.
	 * In un primo momento avevo fatto il contrario (si veda un commit
	 * precedente), ciò rendeva le cose difficili nel momento in cui 
	 * volevo smettere di scrivere poiché dovevo necessariamente digitare
	 * un carattere per innescare il controllo del while...
	 * Mi raccomando lega il controllo del processo "principale" al server
	 * in modo tale che la sua chiusura si propaghi fino al client e
	 * che quindi si esca dal metodo (ascolta()) e si invochi exit.
	 * 
	 * In realtà ho appena verificato che se chiusuraConnessione() viene
	 * invocato in ascolta() e viene eseguita l'istruzione di chiusura
	 * di console, allora il controllo dell'esecuzione passa al thread
	 * che mi richiede un input da tastiera tramite la console. Una volta
	 * fatto il controllo ritorna la processo "principale" e l'esecuzione
	 * termina.
	 */
}
