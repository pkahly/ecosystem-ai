package main;

import java.util.Iterator;
import data.entities.animal.Herbavore;
import data.entities.animal.DeadAnimal;
import data.entities.animal.GeneticCode;
import data.entities.animal.Carnivore;
import data.entities.plant.Plant;
import data.world.World;
import gui.LegacyMainFrame;
import util.RandomUtil;

public class LegacyMain {
	private static final int HEIGHT = 100;
	private static final int WIDTH = HEIGHT * 2;
	
	private static final int PLANTS = WIDTH * 10;
	private static final int ANIMALS = 100;
	private static final int IMMORTAL_CARNIVORES = 1;
	
	private static final int STAT_FREQUENCY = 2000;
	private static final int PRECOMPUTE_TICKS = 0;
	
	public static void main(String[] args) {
		// Create world
		World world = new World(HEIGHT, WIDTH);
		
		for (int i = 0; i < PLANTS; i++) {
			world.addOrReplace(new Plant(), RandomUtil.randPos(HEIGHT, WIDTH));
		}
		
		for (int i = 0; i < ANIMALS; i++) {
			world.addOrReplace(new Herbavore(new GeneticCode()), RandomUtil.randPos(HEIGHT, WIDTH));
		}
		
		for (int i = 0; i < IMMORTAL_CARNIVORES; i++) {
			world.addOrReplace(new Carnivore(), RandomUtil.randPos(HEIGHT, WIDTH));
		}
		
		System.out.println("Num Animals,Average Sensing Distance,Average Speed,Average Reproduction Chance,Average Base Energy");
		int counter = STAT_FREQUENCY; // Run on first tick
		
		for (int i = 0; i < PRECOMPUTE_TICKS; i++) {
			world.tick();
			counter++;
			
			if (counter >= STAT_FREQUENCY) {
				stats(world.getGeneticData());
				counter = 0;
			}
		}
		
		new LegacyMainFrame(world);
	}

	private static void stats(Iterator<GeneticCode> iterator) {
		if (!iterator.hasNext()) {
			System.out.println("0");
			return;
		}
		
		GeneticCode code = iterator.next();
		int count = 1;
		
		double sensingDistanceAvg = code.sensingDistance;
		double speedAvg = code.speed;
		double reproductionChanceAvg = code.reproductionChance;
		double baseEnergyAvg = code.baseEnergy;
		
		while (iterator.hasNext()) {
			code = iterator.next();
			count++;
			
			sensingDistanceAvg = getNextAverage(sensingDistanceAvg, code.sensingDistance, count);
			speedAvg = getNextAverage(speedAvg, code.speed, count);
			reproductionChanceAvg = getNextAverage(reproductionChanceAvg, code.reproductionChance, count);
			baseEnergyAvg = getNextAverage(baseEnergyAvg, code.baseEnergy, count);
		}
		
		System.out.println(count + "," + Math.round(sensingDistanceAvg) + "," + Math.round(speedAvg) + "," + Math.round(reproductionChanceAvg) + "," + Math.round(baseEnergyAvg));
	}
	
	static double getNextAverage(double oldAvg, int newValue, int count) {
		return oldAvg + (newValue - oldAvg) / count;
	}
}
