package data.entities.plant;

import data.entities.Entity;
import data.world.Position;
import data.world.World;
import util.RandomUtil;

public class DeadPlant extends Entity {
	private static final int COLOR = 0;
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
	public int getColor() {
		return COLOR;
	}
}
