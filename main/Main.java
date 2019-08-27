package main;

import data.entities.NeatManager;
import gui.MainFrame;

public class Main {
	public static void main(String[] args) {
		MainFrame mainframe = new MainFrame();
		new NeatManager(mainframe).run();
	}
}
