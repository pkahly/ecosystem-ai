package data.world;

import java.util.List;

import data.entities.Entity;
import data.entities.Entity.Type;

public class Position {
	private Entity entity;
	private int row;
	private int column;
	private boolean isEmpty = false;
	private boolean isWall = false;

	public Position(Entity entity, int row, int column) {
		if (entity == null) {
			isEmpty = true;
		}
		this.entity = entity;
		this.row = row;
		this.column = column;
	}
	
	public Position(int row, int column, boolean isEmpty, boolean isWall) {
		this.row = row;
		this.column = column;
		this.isEmpty = isEmpty;
		this.isWall = isWall;
	}

	public boolean isEmpty() {
		return isEmpty;
	}
	
	public boolean isWall() {
		return isWall;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

	public boolean isAt(int row2, int column2) {
		return row == row2 && column == column2;
	}
	
	@Override
	public String toString() {
		return row + "," + column;
	}

	public Type getEntityType() {
		if (isEmpty()) {
			return Type.NONE;
		} 
		if (isWall()) {
			return Type.WALL;
		}
		
		return entity.getType();
	}

	public Position findClosestPoint(List<Position> posList) {
		double minDistance = Double.MAX_VALUE;
		Position closestPos = null;
		
		for (Position otherPos : posList) {
		    double distance = distance(otherPos);
			
			if (distance <= minDistance) {
				minDistance = distance;
				closestPos = otherPos;
			}
		}
		
		return closestPos;
	}

	public Entity getEntity() {
		return entity;
	}

	public double distance(Position otherPos) {
		double rowDist = Math.abs(row - otherPos.getRow());
	    double colDist = Math.abs(column - otherPos.getColumn());
	         
	    return Math.hypot(rowDist, colDist);
	}
}
