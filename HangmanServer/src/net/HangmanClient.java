package net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HangmanClient extends Thread {
	

	public static void main(String[] args) {
		String indirizzoIp = "81.56.46.184";
		int port = 16384;
		try {
			Socket clientSocket = new Socket(indirizzoIp, port);
			RemoteClient client = new RemoteClient(clientSocket);
			client.start();
			client.ascolta();
			//System.out.println("chiudi");
			System.exit(0);
		} catch (UnknownHostException e) {
			System.out.println("L'indirizzo ip " + indirizzoIp + " risulta "
					+ "irragiungibile.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * La connessione client-server non presenta problemi se gli host
		 * sono entrambi nella stessa rete (locale). In questo caso
		 * basta che il client sappia l'indirizzo ip del server all'interno
		 * della sottorete e il numero di porta associata al processo in 
		 * esecuzione sul server.
		 * 
		 * Per connettere client e server su reti locali differenti
		 * è stato necessario mettere il client a conoscenza dell'indirizzo
		 * ip del router di confine della sottorete del server e del numero
		 * di porta del processo in esecuzione all'interno del router,
		 * responsabile dell'inoltro al server.
		 * Per permettere ciò ho creato una regola specifica di port
		 * forwarding tramite le impostazioni del router in cui ho
		 * specificato:
		 * -l'ip locale del server;
		 * -la porta di reindirizzamento del router;
		 * -la porta di destinazione, ovvero quella associata al processo
		 * in esecuzione sul processo server.
		 */

	}
}
