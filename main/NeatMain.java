package main;

import data.entities.ai.NeatManager;
import gui.MainFrame;

public class NeatMain {
	public static void main(String[] args) {
		MainFrame mainframe = new MainFrame();
		new NeatManager(mainframe).run();
	}
}
