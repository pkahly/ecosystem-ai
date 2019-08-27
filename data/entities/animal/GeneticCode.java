package data.entities.animal;

import java.util.ArrayList;
import java.util.List;

import data.entities.Entity.Type;
import util.RandomUtil;

public class GeneticCode {
	private static final int HERBAVORE_COLOR = 200;
	private static final int OMNIVORE_COLOR = 100;
	private static final int CARNIVORE_COLOR = 180;
	
	private static final int SENSING_DISTANCE = 10;
	private static final int SPEED = 1;
	private static final int REPRODUCTION_CHANCE = 50;
	private static final int BASE_ENERGY = 100;
	
	public int sensingDistance;
	public int speed;
	public int reproductionChance;
	public int baseEnergy;
	public boolean eatsPlants;
	public boolean eatsMeat;
	
	public GeneticCode() {
		sensingDistance = SENSING_DISTANCE;
		speed = SPEED;
		reproductionChance = REPRODUCTION_CHANCE;
		baseEnergy = BASE_ENERGY;
		eatsPlants = true;
		eatsMeat = false;
	}	
	
	public GeneticCode mutate() {
		GeneticCode childCode = new GeneticCode();
		childCode.sensingDistance = Math.max(2, RandomUtil.mutate(sensingDistance, 4));
		childCode.speed = Math.max(1, RandomUtil.mutate(speed, 4));
		childCode.reproductionChance = Math.max(0, RandomUtil.mutate(reproductionChance, 4));
		childCode.baseEnergy = Math.max(10, RandomUtil.mutate(baseEnergy, 4));
		//childCode.eatsPlants = RandomUtil.mutate(eatsPlants);
		//childCode.eatsMeat = RandomUtil.mutate(eatsMeat);
		return childCode;
	}
	
	public int getColor() {
		Type type = getType();
		
		if (type == Type.HERBAVORE) {
			return HERBAVORE_COLOR;
		} else if (type == Type.OMNIVORE) {
			return OMNIVORE_COLOR;
		} else if (type == Type.CARNIVORE) {
			return CARNIVORE_COLOR;
		}
		
		return 0;
	}
	
	public Type getType() {
		if (eatsPlants && !eatsMeat) {
			return Type.HERBAVORE;
		} else if (eatsPlants && eatsMeat) {
			return Type.OMNIVORE;
		} else if (eatsMeat) {
			return Type.CARNIVORE;
		} else {
			return null;
		}
	}
	
	public List<Type> getFoodTypes() {
		List<Type> foodTypes = new ArrayList<>();
		
		if (eatsPlants && !eatsMeat) {
			foodTypes.add(Type.PLANT);
		} else if (eatsPlants && eatsMeat) {
			foodTypes.add(Type.PLANT);
			foodTypes.add(Type.HERBAVORE);
			foodTypes.add(Type.CORPSE);
		} else if (eatsMeat) {
			foodTypes.add(Type.HERBAVORE);
			foodTypes.add(Type.OMNIVORE);
			foodTypes.add(Type.CORPSE);
		}
		
		return foodTypes;
	}
	
	public String toString() {
		String output = "";
		output += "Sensing Distance: " + sensingDistance;
		output += "  Speed: " + speed;
		output += "  Reproduction Chance: " + reproductionChance;
		output += "  Base Energy: " + baseEnergy;
		output += "  Eats Plants: " + eatsPlants;
		output += "  Eats Meat: " + eatsMeat;
		return output;
	}
}
