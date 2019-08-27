package data.entities;

import java.util.Set;

import data.entities.animal.SmartAnimal;
import data.entities.Entity;
import data.world.Position;
import data.world.World;
import gui.MainFrame;

// TODO enable or find another library
/*
import nl.sandergielisse.mythan.CustomizedSigmoidActivation;
import nl.sandergielisse.mythan.FitnessCalculator;
import nl.sandergielisse.mythan.Mythan;
import nl.sandergielisse.mythan.Network;
import nl.sandergielisse.mythan.Setting;
*/

public class NeatManager {
	private static final int POPULATION_SIZE = 10000;
	private static final int IDEAL_FITNESS = 200000;
	private static final int RUNS = 4;
	
	private MainFrame mainframe;
	
	public NeatManager(MainFrame mainframe) {
		this.mainframe = mainframe;
	}
	
	public void run() {
      // TODO enable or rewrite
	   /*
		Mythan instance = Mythan.newInstance(SmartAnimal.NETWORK_INPUTS, SmartAnimal.NETWORK_OUTPUTS, new CustomizedSigmoidActivation(), new FitnessCalculator() {

			@Override
			public double getFitness(Network network) {
				double sumAvg = 0;
				for (int i = 0; i < RUNS; i++) {
					sumAvg += run(network);
				}
				return sumAvg / RUNS;
			}
			
			@Override
			public void generationFinished(Network network) {
				SmartAnimal animal = new SmartAnimal(network);
				
				mainframe.runLifecycle(animal);
				waitTilDead(animal);
			}
			
			private double run(Network network) {
				World world = new World();
				SmartAnimal animal = new SmartAnimal(network);
				world.addOrReplace(animal, new Position(animal, 10, 20));
				
				while (!animal.isDead()) {
					Set<Entity> processed = world.tick();
					if(!processed.contains(animal)) {
						break;
					}
				}
				
				return animal.getFoodEaten();
			}
		});

		instance.setSetting(Setting.GENE_DISABLE_CHANCE, 0.75);
		instance.setSetting(Setting.MUTATION_WEIGHT_CHANCE, 0.7);
		instance.setSetting(Setting.MUTATION_WEIGHT_RANDOM_CHANCE, 0.10);
		instance.setSetting(Setting.MUTATION_WEIGHT_MAX_DISTURBANCE, 0.1);

		instance.setSetting(Setting.MUTATION_NEW_CONNECTION_CHANCE, 0.3);
		instance.setSetting(Setting.MUTATION_NEW_NODE_CHANCE, 0.008);

		instance.setSetting(Setting.DISTANCE_EXCESS_WEIGHT, 1.0);
		instance.setSetting(Setting.DISTANCE_DISJOINT_WEIGHT, 1.0);
		instance.setSetting(Setting.DISTANCE_WEIGHTS_WEIGHT, 0.4);

		instance.setSetting(Setting.SPECIES_COMPATIBILTY_DISTANCE, 1.0); // the bigger the less species
		instance.setSetting(Setting.MUTATION_WEIGHT_CHANCE_RANDOM_RANGE, 3); // -2.0 - 2.0

		instance.setSetting(Setting.GENERATION_ELIMINATION_PERCENTAGE, 0.25);
		instance.setSetting(Setting.BREED_CROSS_CHANCE, 0.8);

		instance.trainToFitness(POPULATION_SIZE, IDEAL_FITNESS);
		*/
	}

	private void waitTilDead(SmartAnimal animal) {
		try {
			while (!animal.isDead()) {
				Thread.sleep(1000);
			}
			
			Thread.sleep(250);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
