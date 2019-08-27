package data.entities.animal;

import java.util.ArrayList;
import java.util.List;

import data.entities.Entity;
import data.world.Position;
import data.world.World;
import util.RandomUtil;

public class Herbavore extends Entity {
	private static final int ACTION_DISTANCE = 1;

	private final int ENERGY_USAGE;
	private final int HUNGER_THRESHOLD;
	private final int REPRODUCTION_COST;
	
	private GeneticCode config;
	protected int energy;
	
	public Herbavore(GeneticCode config) {
		super(config.getType());
		this.config = config;
		
		ENERGY_USAGE = (int) Math.round(getBaseEnergy() * 0.1); 
		HUNGER_THRESHOLD = getBaseEnergy() * 2;
		REPRODUCTION_COST = getBaseEnergy() * 4;
	}
	
	@Override
	public void tick(World world, Position pos) {
		if (getEnergy() < HUNGER_THRESHOLD) {
			// Feeding
			pos = seekFood(world, pos);
		} else if (getEnergy() - REPRODUCTION_COST >= getBaseEnergy()){
			// Reproduction
			for (Position adjacentPos : world.getNearbyPositionsOfType(pos, ACTION_DISTANCE, Type.NONE)) {
				if (RandomUtil.occurs(config.reproductionChance)) {
					world.addOrReplace(new Herbavore(config.mutate()), adjacentPos);
					subtractEnergy(REPRODUCTION_COST);
				}
			}
		}
		
		// Energy Usage
		double abilityScore = (config.speed + (config.sensingDistance / 10.0));
		subtractEnergy((int) Math.round(ENERGY_USAGE * abilityScore));
		
		// Death
		if (getEnergy() <= 0) {
			world.addOrReplace(new DeadAnimal(), pos);
		}
		
		// Aging
		age++;
	}

	private Position seekFood(World world, Position pos) {
		// Eat Food If Possible
		List<Position> foodWithinReach = world.getNearbyPositionsOfType(pos, ACTION_DISTANCE, config.getFoodTypes());
		if (!foodWithinReach.isEmpty()) {
			for (Position foodPos : foodWithinReach) {
				eatEntity(world.remove(foodPos));
			}
			return pos;
		}
		
		// Move toward food
		List<Position> food = new ArrayList<>();
		
		for (Position adjacentPos : world.getNearbyPositionsOfType(pos, config.sensingDistance, config.getFoodTypes())) {
			food.add(adjacentPos);
		}
		
		Position closestFood = pos.findClosestPoint(food);
				
		if (closestFood != null) {
			Position spotToMoveTo = closestFood.findClosestPoint(world.getNearbyPositionsOfType(pos, config.speed, Type.NONE));
			
			if (spotToMoveTo != null) {
				world.move(pos, spotToMoveTo);
				return world.getUpdatedPosition(spotToMoveTo);
			}
		}
		
		return pos;
	}

	private void eatEntity(Entity food) {
		if (food.type == Type.PLANT) {
			addEnergy(200);
		} else if (food.type == Type.HERBAVORE) {
			addEnergy(1000);
		} else if (food.type == Type.CORPSE) {
			addEnergy(1000);
		} else if (food.type == Type.OMNIVORE) {
			addEnergy(5000);
		} else {
			throw new RuntimeException("Invalid food type: " + food.type);
		}
	}

	@Override
	public int getColor() {
		return config.getColor();
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public void addEnergy(int newEnergy) {
		energy += newEnergy;
	}
	
	protected int getBaseEnergy() {
		return config.baseEnergy;
	}

	public void subtractEnergy(int energySpent) {
		energy -= energySpent;
		energy = Math.max(energy, 0);
	}

	public GeneticCode getGenetics() {
		return config;
	}
}
