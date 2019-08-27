package data.entities.animal;

import java.awt.Color;
import data.entities.Entity;
import data.world.Position;
import data.world.World;
//import nl.sandergielisse.mythan.Network;

public class SmartAnimal extends Entity {
	public static final int NETWORK_INPUTS = 18;
	public static final int NETWORK_OUTPUTS = 4;
	public static final Color COLOR = Color.ORANGE;
	public static final int STARVATION_TIMER = 20;
	
	//private Network network;
	private boolean isDead = false;
	private int foodEaten = 0;

	public SmartAnimal() {//Network network) {
		super(Type.HERBAVORE);
		//this.network = network;
	}

	@Override
	public Color getColor() {
		return COLOR;
	}

	@Override
	public void tick(World world, Position pos) {
		if (isDead) {
			return;
		}
		
		// Stats
		age++;
		
		// Use neural network
		Position newPos = getNextMove(world, pos);

		// Decide what to do
		newPos = world.getUpdatedPosition(newPos);
		if (newPos == null) {
			// Do Nothing
		} else if (newPos.getEntityType() == Type.NONE) {
			// Move to new position
			world.move(pos, newPos);
		} else if (newPos.getEntityType() == Type.PLANT) {
			// Feeding
			foodEaten++;
			world.move(pos, newPos);
		}
		
		// If hunger drops to 0, die
		if (age > 20) {
			isDead = true;
		}
	}
	
	private Position getNextMove(World world, Position pos) {
		Position directionPos;
		double[] inputs = new double[NETWORK_INPUTS];
		
		// Bias value
		inputs[0] = 1;
		inputs[1] = -1;
		
		// North West
		directionPos = world.getNearestObject(pos, -1, -1);
		inputs[2] = directionPos.getEntityType().ordinal();
		inputs[3] = directionPos.distance(pos);
		
		// North
		directionPos = world.getNearestObject(pos, -1, 0);
		inputs[4] = directionPos.getEntityType().ordinal();
		inputs[5] = directionPos.distance(pos);
		
		// North East
		directionPos = world.getNearestObject(pos, -1, 1);
		inputs[6] = directionPos.getEntityType().ordinal();
		inputs[7] = directionPos.distance(pos);
		
		// East
		directionPos = world.getNearestObject(pos, 0, 1);
		inputs[8] = directionPos.getEntityType().ordinal();
		inputs[9] = directionPos.distance(pos);
		
		// South East
		directionPos = world.getNearestObject(pos, 1, 1);
		inputs[10] = directionPos.getEntityType().ordinal();
		inputs[11] = directionPos.distance(pos);
		
		// South
		directionPos = world.getNearestObject(pos, 1, 0);
		inputs[12] = directionPos.getEntityType().ordinal();
		inputs[13] = directionPos.distance(pos);
		
		// South West
		directionPos = world.getNearestObject(pos, 1, -1);
		inputs[14] = directionPos.getEntityType().ordinal();
		inputs[15] = directionPos.distance(pos);
		
		// West
		directionPos = world.getNearestObject(pos, 0, -1);
		inputs[16] = directionPos.getEntityType().ordinal();
		inputs[17] = directionPos.distance(pos);
		
		// Convert relative movements to coordinates
		double[] outputs = new double[4];//network.calculate(inputs);
		int newRow = pos.getRow() + getCoordFromOutput(outputs[0], outputs[1]);
		int newCol = pos.getColumn() + getCoordFromOutput(outputs[2], outputs[3]);
		
		return new Position(this, newRow, newCol);
	}

	public int getFoodEaten() {
		return foodEaten;
	}

	public boolean isDead() {
		return isDead;
	}
	
	public int getCoordFromOutput(double output1, double output2) {
		if (output1 > output2 - 0.1) {
			return 1;
		} if (output2 > output1 - 0.1) {
			return -1;
		}
		
		return 0;
	}
}
