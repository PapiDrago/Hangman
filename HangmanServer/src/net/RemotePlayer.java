package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import console.LocalPlayer;
import hangman.Game;
import hangman.Player;

public class RemotePlayer extends LocalPlayer {
	private Socket socket;
	private BufferedReader inputStream;
	private PrintWriter outputStream;

	public RemotePlayer(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.outputStream = new PrintWriter(socket.getOutputStream());
		this.outputStream.println("Connessione stabilita!");
	}

	@Override
	public char chooseLetter(Game game) {
		String line = null;
		try {
			while ((line = inputStream.readLine()) != null) {            
				line = line.trim();
				if (line.length() == 1 && Character.isLetter(line.charAt(0))) {
					return line.charAt(0);
				} else {
					outputStream.println("Lettera non valida.");
				}
			}
		} catch (IOException e) {
			 line = "";
		}
		return line.charAt(0);
	}
	
	@Override
    public void update(Game game) {
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
                outputStream.print("\n" + rem + " tentativi rimasti\n");
                outputStream.println(this.gameRepresentation(game));
                outputStream.println(game.getKnownLetters());
                break;
        }
    }
	
	 private void printBanner(String message) {
		 outputStream.println("");
	        for (int i = 0; i < 80; i++)
	            System.out.print("*");
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

}
