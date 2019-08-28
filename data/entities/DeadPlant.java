package data.entities;

import java.awt.Color;
import data.entities.Entity;
import data.world.Position;
import data.world.World;
import util.RandomUtil;

public class DeadPlant extends Entity {
	private static final Color COLOR = Color.BLACK;
	private static final int DECAY_CHANCE_PER_AGE = 4;
	
	public DeadPlant() {
		super(Type.PLANT);
	}

	@Override
	public void tick(World world, Position pos) {
		// Decay
		int decayChance = age * DECAY_CHANCE_PER_AGE;
		if (RandomUtil.occurs(decayChance)) {
			world.remove(pos);
		}
		
		// Aging
		age++;
	}

	@Override
	public Color getColor() {
		return COLOR;
	}
}
