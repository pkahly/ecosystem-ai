package data.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import data.entities.Entity;
import data.entities.Entity.Type;
import data.entities.animal.Herbavore;
import data.entities.animal.GeneticCode;
import data.entities.plant.Plant;
import util.RandomUtil;

public class World {
	private static final int HEIGHT = 20;
	private static final int WIDTH = HEIGHT * 2;
	private static final int REFOREST_CHANCE = 1;
	private static final int NUM_PLANTS = 10;
	
	private int height;
	private int width;
	private Entity[][] worldArray;

	public World() {
		this(HEIGHT, WIDTH);
		
		for (int i = 0; i < NUM_PLANTS; i++) {
			addOrReplace(new Plant(), RandomUtil.randPos(HEIGHT, WIDTH));
		}
	}
	
	public World(int height, int width) {
		this.height = height;
		this.width = width;		
		worldArray = new Entity[height][width];
	}
	
	public Set<Entity> tick() {
		Set<Entity> processed = new HashSet<Entity>();
		
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				Entity entity = worldArray[row][column];
				Position pos = new Position(entity, row, column);
				
				if (entity != null && !processed.contains(entity)) {
					processed.add(entity);
					entity.tick(this, pos);
				} else if (RandomUtil.occurs(REFOREST_CHANCE)) {
					addOrReplace(new Plant(), pos);
				}
			}
		}
		
		return processed;
	}
	
	public Iterator<GeneticCode> getGeneticData() {
		List<GeneticCode> genetics = new ArrayList<>();
		
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				Entity entity = worldArray[row][column];
				if (entity instanceof Herbavore) {
					Herbavore animal = (Herbavore) entity;
					genetics.add(animal.getGenetics());
				}
			}
		}
		
		return genetics.iterator();
	}
	
	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public Color getColorValue(int row, int column) {
		Entity entity = worldArray[row][column];
		
		if (entity != null) {
			return entity.getColor();
		}
		
		return new Color(250, 250, 232);
	}
	
	public Position getUpdatedPosition(Position pos) {
		return getUpdatedPosition(pos.getRow(), pos.getColumn());
	}
	
	public Position getUpdatedPosition(int row, int column) {
		if (!isValidPosition(row, column)) {
			return null;
		}
		
		return new Position(worldArray[row][column], row, column);
	}
	
	
	public void addOrReplace(Entity entity, Position pos) {
		if (isValidPosition(pos)) {
			worldArray[pos.getRow()][pos.getColumn()] = entity;
		}
	}
	
	public void move(Position fromPos, Position toPos) {
		if (isValidPosition(toPos)) {
			addOrReplace(remove(fromPos), toPos);
		}
	}

	public Entity remove(Position pos) {
		pos = getUpdatedPosition(pos);
		Entity entity = pos.getEntity();
		
		addOrReplace(null, pos);
		
		return entity;
	}

	public List<Position> getNearbyPositionsOfType(Position center, int sensingDistance, Type type) {
		List<Type> list = new ArrayList<>();
		list.add(type);		
		return getNearbyPositionsOfType(center, sensingDistance, list);
	}
	
	public List<Position> getNearbyPositionsOfType(Position center, int sensingDistance, List<Type> types) {
		int startRow = center.getRow() - sensingDistance;
		int endRow = center.getRow() + sensingDistance;
		int startCol = center.getColumn() - sensingDistance;
		int endCol = center.getColumn() + sensingDistance;
		
		List<Position> nearbyPositions = new ArrayList<>();
		for (int row = startRow; row <= endRow; row++) {
			for (int col = startCol; col <= endCol; col++) {
				if (isValidPosition(row, col)) {
					Position pos = new Position(worldArray[row][col], row, col);
					if (types.contains(pos.getEntityType())) {
						nearbyPositions.add(pos);
					}
				}
			}
		}
		
		return nearbyPositions;
	}
	
	private boolean isValidPosition(Position pos) {
		return isValidPosition(pos.getRow(), pos.getColumn());
	}
	
	private boolean isValidPosition(int row, int column) {
		if (row < 0 || column < 0) {
			return false;
		}
		
		if (row >= height || column >= width) {
			return false;
		}
		
		return true;
	}

	public Herbavore findFirstAnimal() {
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				Entity entity = worldArray[row][column];
				
				if (entity instanceof Herbavore) {
					return (Herbavore) entity;
				}
			}
		}
		
		return null;
	}

	public Position getNearestObject(Position pos, int rowMod, int colMod) {
		int row = pos.getRow() + rowMod;
		int col = pos.getColumn() + colMod;
		
		while (isValidPosition(row, col)) {
			Position newPos = getUpdatedPosition(row, col);
			if (!newPos.isEmpty()) {
				return newPos;
			}
			
			row += rowMod;
			col += colMod;
		}
		
		return new Position(row, col, false, true); // Wall
	}
}
