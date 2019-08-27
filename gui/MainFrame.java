package gui;

import java.awt.*;
import javax.swing.*;

import data.entities.ai.SmartAnimal;
import data.world.Position;
import data.world.World;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	ImagePanel imagePanel;
	
	public MainFrame() {
		imagePanel = new ImagePanel();
		
		Container cp = getContentPane();
		cp.add(imagePanel);
		
		setupMainFrame();
	}
	   
	private void setupMainFrame() {   
		Toolkit tk;
		Dimension d;
	      
		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
		
		setSize(d.width / 2, d.height / 2);
		setLocation(0, 0);
		  
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setTitle("Environment Simulator");
		setVisible(true);
	}

	public void runLifecycle(SmartAnimal animal) {
		World world = new World();
		world.addOrReplace(animal, new Position(animal, 10, 20));
		imagePanel.runLifecycle(world, animal);
	}
}
