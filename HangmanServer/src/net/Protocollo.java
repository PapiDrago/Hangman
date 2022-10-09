package net;

import hangman.Dictionary;
import hangman.Game;
import hangman.GameResult;

public class Protocollo {
	private Game gioco;
	private Dictionary diz;

	public Protocollo() {
		this.diz = new Dictionary();
		this.gioco = null;
	}

	public String processaInput(String input) {
		String output = "";
		if(input.equalsIgnoreCase("gioca") && this.gioco == null) {
			this.gioco = new Game(diz.pickWord());
			output = "Hai scelto di giocare all'impiccato!";
			//System.out.println(gioco.getSecretWord());
			return output + "\r\n\n           " +
			this.gioco.getKnownLetters();
		} else if (this.gioco == null) {
			output = input;
			return "Echo " + output;
		} else if ((gioco != null) && (this.gioco.getResult() == GameResult.OPEN)) {
			if(isInputOk(input)) {
				this.gioco.makeAttempt(input.charAt(0));
				if(gioco.getResult() == GameResult.SOLVED) {
					output = this.gioco.getKnownLetters() + "\n" +
							"Congratulazioni! Ti sei salvato!";
					gioco = null;
					return output;
				} else if(gioco.getResult() == GameResult.FAILED) {
					output = "\n" + gameRepresentation() + "\n" +
							this.gioco.getKnownLetters() +  
							"\nOh no! Sei stato impiccato!\n" +
							"La parola era " + gioco.getSecretWord();
					gioco = null;
					return output;	
				}
				return "\r\n\n           " + this.gioco.getKnownLetters() + "\r\n" +
				gameRepresentation();
			}
			output = "\nDevi scrivere solamente un carattere uguale a "
					+ "una lettera dell'alfabeto!\n" +
					this.gioco.getKnownLetters() + "\r\n" +
					gameRepresentation();
		}
		return output;
	}

	private String gameRepresentation() {
		int a = gioco.countFailedAttempts();

		String s = "___________\r\n/         |\r\n|         ";
		s += (a == 0 ? "" : "O\r\n");
		s += "|       " + (a == 0 ? "  \r\n" : (a < 5 ? "  +\r\n" :
			(a == 5 ? "--+\r\n" : "--+--\r\n")));
		s += "|         " + (a < 2 ? "\r\n" : "|\r\n");
		s += "|        " + (a < 3 ? " \r\n" : (a > 3 ? "/ \\\r\n" : "/\r\n" ));
		s += "|";
		return s;
	}
	/*
	 * NB il carattere '+' viene aggiunto insieme alla testa, non corrisponde
	 * a un errore fatto.
	 */
	private boolean isInputOk(String input) {
		if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
			return true;
		}
		return false;
	}

}
