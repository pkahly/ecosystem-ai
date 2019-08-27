package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.Timer;
import data.entities.Entity;
import data.entities.ai.SmartAnimal;
import data.world.World;
import util.ImageUtil;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements ActionListener {
	private static final int TICK = 50;
	
	private Timer timer;
	private World world;
	private SmartAnimal animal;
	
	public void runLifecycle(World world, SmartAnimal animal) {
		this.world = world;
		this.animal = animal;
		
		// Start solver timer
		timer = new Timer(TICK, this);
		timer.setActionCommand("TIMER");
		timer.setCoalesce(true);
		timer.start();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Set<Entity> processed = world.tick();
		
		if (animal.isDead() || !processed.contains(animal)) {
			timer.stop();
		}
		
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Generate new image of the world's current state
		BufferedImage image = ImageUtil.createImage(world);
		
		// Scale and display
		BufferedImage scaledImage = ImageUtil.resizeImage(image, getWidth(), getHeight());
		g.drawImage(scaledImage, 0, 0, null);
	}
}
