package gui;

import java.awt.*;
import javax.swing.*;
import data.world.World;

@SuppressWarnings("serial")
public class LegacyMainFrame extends JFrame {
		
	public LegacyMainFrame(World world) {
		LegacyImagePanel imagePanel = new LegacyImagePanel(world);
		
		Container cp = getContentPane();
		cp.add(imagePanel);
		
		setupMainFrame();
	}
	   
	private void setupMainFrame() {   
		Toolkit tk;
		Dimension d;
	      
		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
		
		setSize(d.width, d.height);
		setLocation(0, 0);
		  
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setTitle("Environment Simulator");
		setVisible(true);
	}
}
