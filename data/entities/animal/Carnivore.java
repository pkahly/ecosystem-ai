package data.entities.animal;

import java.util.ArrayList;
import java.util.List;

import data.entities.Entity;
import data.world.Position;
import data.world.World;

public class Carnivore extends Entity {
	private static final int SENSING_DISTANCE = 10;
	private static final int ACTION_DISTANCE = 1;
	private static final int COLOR = 180;
	private static final int SPEED = 1;

	private List<Type> foodTypes = new ArrayList<>();
	
	public Carnivore() {
		super(Type.CARNIVORE);
		
		foodTypes.add(Type.HERBAVORE);
		foodTypes.add(Type.OMNIVORE);
		foodTypes.add(Type.CORPSE);
	}
	
	@Override
	public void tick(World world, Position pos) {
		seekFood(world, pos);
	}

	private Position seekFood(World world, Position pos) {
		// Eat Food If Possible
		List<Position> foodWithinReach = world.getNearbyPositionsOfType(pos, ACTION_DISTANCE, foodTypes);
		if (!foodWithinReach.isEmpty()) {
			for (Position foodPos : foodWithinReach) {
				world.remove(foodPos);
			}
			return pos;
		}
		
		// Move toward food
		List<Position> food = new ArrayList<>();
		
		for (Position adjacentPos : world.getNearbyPositionsOfType(pos, SENSING_DISTANCE, foodTypes)) {
			food.add(adjacentPos);
		}
		
		Position closestFood = pos.findClosestPoint(food);
				
		if (closestFood != null) {
			Position spotToMoveTo = closestFood.findClosestPoint(world.getNearbyPositionsOfType(pos, SPEED, Type.NONE));
			
			if (spotToMoveTo != null) {
				world.move(pos, spotToMoveTo);
				return world.getUpdatedPosition(spotToMoveTo);
			}
		}
		
		return pos;
	}

	@Override
	public int getColor() {
		return COLOR;
	}
}
