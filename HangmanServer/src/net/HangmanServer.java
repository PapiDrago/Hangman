/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.IOException;
import java.net.Socket;
import hangman.Hangman;
import hangman.Player;

/**
 *
 * @author Claudio Guarrasi
 */
public class HangmanServer extends Thread {
	private Socket socket;

	public HangmanServer(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		super.run();
		Hangman game = new Hangman();
		Player player;
		try {
			player = new RemotePlayer(this.socket);
			game.playGame(player);
			System.out.println("server chiuso");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
