package net;

import java.io.IOException;
import java.net.ServerSocket;

public class HangmanMultiServer {

	public static void main(String[] args) {
		int port = 770;
		/*
		 * 770 è stato scelto arbitrariamente tra i numeri di porta non utilizzati
		 * per saperlo ho utilizzato il comando netstat -a sul cmd di Windows.
		 * Sul cmd di Windows per reperire info sul comando bisogna scrivere /? dopo
		 * il comando.
		 */

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(true) {
				new HangmanServer(serverSocket.accept()).start();
				//break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Ho istanziato un socket di tipo serverSocket. Questo
		 * oggetto astrae il concetto del socket, sempre in ascolto,
		 * di un server. Infatti tipicamente usando il protocollo
		 * a livello trasporto TCP, il processo client contatta
		 * il processo server per stabilire una connessione.
		 * Il client conosce l'hostname e il numero d'ordine della
		 * porta della socket "di benvenuto" del server quella che è
		 * sempre in ascolto in attesa di richieste.
		 */

		/*
		 * Quando il client contatta il server sulla porta in ascolto
		 * allora il server crea una socket dedicata alla connessione
		 * (e quindi trasmissione) col client che l'ha contattato che ha
		 * lo stesso numero di porta di quella in ascolto.
		 * Cio' spiega la linea di codice precedente: la classe
		 * Socket viene usata per catturare il concetto di socket
		 * come endpoint di una connessione TCP che è costituita da
		 * due endpoint.
		 * Ciascun endpoint è caratterizzato da un un indirizzo IP e da un 
		 * numero di porta.
		 * NB quando un client contatta il server lo fa dal suo endpoint.
		 * Le coordinate di quest'ultimo sono note al server quando si
		 * stabilisce il contatto.
		 */

		/*
		 * NB grazie all'uso del thread per svolgere la comunicazione
		 * client-server, l'altro processo, quello "principale" è in
		 * attesa di una nuova richiesta di un client
		 */

	}

}
