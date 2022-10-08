/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import hangman.Hangman;
import hangman.Player;

/**
 *
 * @author Claudio Guarrasi
 */
public class HangmanServer {


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
			/*
			 * Ho instanziato un socket di tipo serverSocket. Questo
			 * oggetto astrae il concetto del socket, sempre in ascolto,
			 * di un server. Infatti tipicamente usando il protocollo
			 * a livello trasporto TCP, il processo client contatta
			 * il processo server per stabilire una connessione.
			 * Il client conosce l'hostname e il numero d'ordine della
			 * porta della socket "di benvenuto" del server quella che è
			 * sempre in ascolto in attesa di richieste.
			 */
			Socket socket = serverSocket.accept();
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
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
			/*
			 * Siccome il protocollo applicativo sara' semplice poiche' client e server
			 * si scambieranno caratteri, decido di istanziare gli stream di input
			 * e di output che si usano tipicamente per gestire semplici caratteri.
			 * NB il costruttore di BufferedReader che richiede che sia passato come
			 * argomento un oggetto Reader. Cio' si fa perche' se usassimo solo
			 * un oggetto di tipo InputStreamReader, ogni volta che ne usiamo il metodo
			 * read() (o altri) viene richiesta una sequenza di caratteri direttamente
			 * dal disco rigido: cio' e' un'operazione lunga e se faccio tante letture
			 * aumento la lungaggine. Dunque e' bene avvolgere questi tipi di Reader
			 * in un BufferedReader che memorizza una sequenza di byte nella RAM,
			 * in questo modo quando usiamo il metodo Read andiamo a cercare il byte
			 * nel buffer che e' un'operazione piu' veloce. Ovviamente quando
			 * il buffer si svuota dovremo obbligatoriamente andare in memoria lenta.
			 * 
			 * NB il true nel costruttore del PrintWriter imposta il flush in modalita'
			 * automatica: es. ogni volta che invoco println il buffer in output
			 * sarà completamente svuotato; mi assicuro che tutto ciò che c'e' nello
			 * stream venga scritto.
			 */
			outputStream.println("Connessione stabilita!");
			Protocollo p = new Protocollo();

			String message = null;
			while((message = inputStream.readLine()) != null) {
				outputStream.println(p.processaInput(message));
				if(message.equalsIgnoreCase("chiudi")) {
					break;
				}
			}

		} catch (IOException e) {
			System.out.println("Non è possibile usare il numero d'ordine "
					+ port + ".");
			e.printStackTrace();
		}

	}

}
