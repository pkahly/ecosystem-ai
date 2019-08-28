package util;

import java.util.List;
import java.util.Random;

import data.world.Position;

public class RandomUtil {
	private static final int CHANCE_SPACE = 10000;

	private static Random rand = new Random();

	public static boolean occurs(int chance) {
		int randNum = rand.nextInt(CHANCE_SPACE);
		return randNum < chance;
	}

	public static Position randPos(int height, int width) {
		return new Position(rand.nextInt(height), rand.nextInt(width), true, false);
	}

	public static Position choosePos(List<Position> posList) {
		int index = rand.nextInt(posList.size());
		return posList.get(index);
	}
}
