package net;

import hangman.Dictionary;
import hangman.Game;

public class Protocollo {
	private Game gioco;
	private Dictionary diz;
	private boolean hangman;

	public Protocollo() {
		super();
		this.diz = new Dictionary();
		this.gioco = new Game(diz.pickWord());
		this.hangman = false;
	}

	public String processaInput(String input) {
		String output = null;
		if(input.equalsIgnoreCase("gioca") && !hangman) {
			this.hangman = true;
			output = "Hai scelto di giocare all'impiccato!";
			return output + "\n" + this.gioco.getKnownLetters();
		} else if (!hangman) {
			output = input;
			return "Echo " + output;
		} else if (hangman) {
			if(Character.isLetter(input.charAt(0))) {
				this.gioco.makeAttempt(input.charAt(0));
				return this.gioco.getKnownLetters();
			}


		}
		return output;

	}
}
