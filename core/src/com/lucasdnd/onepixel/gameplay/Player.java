package com.lucasdnd.onepixel.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Inventory;
import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.world.Exchanger;
import com.lucasdnd.onepixel.gameplay.world.MapObject;
import com.lucasdnd.onepixel.gameplay.world.World;

public class Player {
	
	public static final int MAX_STAT_VALUE = 10000;
	private int x, y, z, direction;
	public final int UP = 0;
	public final int LEFT = 1;
	public final int DOWN = 2;
	public final int RIGHT = 3;
	private Color color;
	private Inventory inventory;
	
	private int health, stamina, food, drink;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.BLACK;
		health = MAX_STAT_VALUE;
		stamina = MAX_STAT_VALUE;
		food = MAX_STAT_VALUE;
		drink = MAX_STAT_VALUE;
		
		faceUp();
		inventory = new Inventory(27);
	}

	public void update() {
		
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(x * OnePixel.PIXEL_SIZE, y * OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
		float directionOffsetX = 0f;
		float directionOffsetY = 0f;
		switch (direction) {
		case UP:
			directionOffsetX = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE + OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case LEFT:
			directionOffsetX = -OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case DOWN:
			directionOffsetX = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = -OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case RIGHT:
			directionOffsetX = OnePixel.PIXEL_SIZE + OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		default:
			break;
		}
		sr.end();
	}
	
	public void performAction(World world) {
		int[] target = getTargetBlock();
		int targetX = target[0];
		int targetY = target[1];
		int targetZ = target[2];
		
		MapObject targetObject = world.getMapObjectAt(targetX, targetY, targetZ);
		if (targetObject == null) {
			return;
		}
		
		Object result = targetObject.performAction();
		if (result != null) {
			stamina -= 10;
			if (result instanceof Item) {
				inventory.addItem((Item)result);
			}
		}
	}
	
	public void placeBlock(World world) {
		int[] target = getTargetBlock();
		int targetX = target[0];
		int targetY = target[1];
		int targetZ = target[2];
		
		MapObject targetObject = world.getMapObjectAt(targetX, targetY, targetZ);
		if (targetObject != null) {
			return;
		}
		
		Item item = inventory.getContent().get(inventory.getContent().size() - 1);
		MapObject itemBlock = Exchanger.exchange(item, world, targetX, targetY, targetZ);
		world.getMapObjects()[targetX][targetY][targetZ] = itemBlock;
		stamina -= 10;
	}
	
	private int[] getTargetBlock() {
		int[] result = new int[3];
		final int X = 0;
		final int Y = 1;
		final int Z = 2;
		result[X] = x;
		result[Y] = y;
		result[Z] = z;
		if (direction == UP) {
			result[Y]++;
		} else if (direction == DOWN) {
			result[Y]--;
		} else if (direction == RIGHT) {
			result[X]++;
		} else if (direction == LEFT) {
			result[X]--;
		}
		return result;
	}
	
	/** Facing methods */
	
	public void faceUp() {
		direction = UP;
	}
	
	public void faceLeft() {
		direction = LEFT;
	}
	
	public void faceDown() {
		direction = DOWN;
	}
	
	public void faceRight() {
		direction = RIGHT;
	}

	/** Movement Methods */
	
	public boolean canMoveUp(World world) {
		faceUp();
		return y + 1 < world.getSize() && world.getMapObjects()[x][y+1][0] == null;
	}

	public boolean canMoveLeft(World world) {
		faceLeft();
		return x - 1 >= 0 && world.getMapObjects()[x-1][y][0] == null;
	}

	public boolean canMoveDown(World world) {
		faceDown();
		return y - 1 >= 0 && world.getMapObjects()[x][y-1][0] == null;
	}

	public boolean canMoveRight(World world) {
		faceRight();
		return x + 1 < world.getSize() && world.getMapObjects()[x+1][y][0] == null;
	}
	
	public void moveUp() {
		stamina--;
		y++;
	}
	
	public void moveDown() {
		stamina--;
		y--;
	}
	
	public void moveRight() {
		stamina--;
		x++;
	}
	
	public void moveLeft() {
		stamina--;
		x--;
	}

	/** Getters and setters */
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getDrink() {
		return drink;
	}

	public void setDrink(int drink) {
		this.drink = drink;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
}