package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class WoodBlock extends MapObject {
	
	final static Color color = Color.BROWN;

	public WoodBlock(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.pixelSize, OnePixel.pixelSize);
	}

	@Override
	public Object performAction() {
		disposer.dispose(this);
		return new Wood();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
