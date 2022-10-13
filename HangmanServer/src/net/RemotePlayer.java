package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import hangman.Game;
import hangman.Player;

public class RemotePlayer extends Player {
	private Socket socket;
	private BufferedReader inputStream;
	private PrintWriter outputStream;

	public RemotePlayer(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.outputStream = new PrintWriter(socket.getOutputStream(), true);
		this.outputStream.println("\nCiao, sono il boia.\nSei stato"
				+ " condannato all'impiccagione!\n"
				+ "Ti salverai solamente se indovinerai la parola!\n"
				+ "Scrivi il carattere alfabetico che pensi sia contenuto nella parola segreta.");
	}
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
	@Override
	public char chooseLetter(Game game) {
		String line = "";
		while (true) {
			try {
				outputStream.println("Inserisci una lettera");
				line = inputStream.readLine();
			} catch (IOException e) {
				line = "";
			}
			if (line.equalsIgnoreCase("chiudi")) {
				outputStream.println("ciao");
				System.exit(0);
			}
			line = line.trim();
			if (line.length() == 1 && Character.isLetter(line.charAt(0))) {
				return line.charAt(0);
			} else {
				outputStream.println("Lettera non valida.");
			}
		}
	}

	@Override
	public void update(Game game) {
		String output = null;
		switch(game.getResult()) {
		case FAILED:
			printBanner("Hai perso!  La parola da indovinare era '" +
					game.getSecretWord() + "'");
			break;
		case SOLVED:
			printBanner("Hai indovinato!   (" + game.getSecretWord() + ")");
			break;
		case OPEN:
			int rem = Game.MAX_FAILED_ATTEMPTS - game.countFailedAttempts();
			//outputStream.print("\n" + rem + " tentativi rimasti\n");
			//outputStream.println(this.gameRepresentation(game));
			//outputStream.println(game.getKnownLetters());
			output = "\n" + rem + " tentativi rimasti\n" + this.gameRepresentation(game)
						+ game.getKnownLetters();
			outputStream.println(output);
			break;
		}
	}

	private void printBanner(String message) {
		outputStream.println("");
		for (int i = 0; i < 80; i++)
			outputStream.print("*");
		outputStream.println("\n***  " + message);
		for (int i = 0; i < 80; i++)
			outputStream.print("*");
		outputStream.println("\n");
	}

	private String gameRepresentation(Game game) {
		int a = game.countFailedAttempts();

		String s = "   ___________\n  /       |   \n  |       ";
		s += (a == 0 ? "\n" : "O\n");
		s += "  |     " + (a == 0 ? "\n" : (a < 5
				? "  +\n"
						: (a == 5 ? "--+\n" : "--+--\n")));
		s += "  |       " + (a < 2 ? "\n" : "|\n");
		s += "  |      " + (a < 3 ? "\n" : (a == 3 ? "/\n" : "/ \\\n"));
		s += "  |\n================\n";
		return s;
	}

	@Override
	public boolean keepPlaying() {
		outputStream.println("Se vuoi continuare a giocare scrivi y.");
		try {
			if(inputStream.readLine().equalsIgnoreCase("y")) {
				return true;
			}
			chiusuraConnessione();
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	private void chiusuraConnessione() throws IOException {
		outputStream.println("Grazie per ver giocato!");
		inputStream.close();
		outputStream.close();
		this.socket.close();
	}

}
