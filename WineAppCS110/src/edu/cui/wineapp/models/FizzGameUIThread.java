package edu.cui.wineapp.models;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import edu.cui.wineapp.models.AndroObject;

public class FizzGameUIThread extends Thread {

	List<AndroObject> objects;
	
	SurfaceHolder surface;
	
	AndroObject selected;
	
	public FizzGameUIThread(SurfaceHolder surface) {
		this.surface = surface;
		objects = new CopyOnWriteArrayList<AndroObject>();
	}
	
	// flag to hold game state
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		while (running) {
			for (AndroObject a: objects) {
				if (a != null) {
					a.move();
					if (a.getY()>Sizes.screenHeight+40)
					{
						a.setPosition(a.getX(), -40);
					}
					else if (a.getY() < -40)
					{
						a.setPosition(a.getX(), Sizes.screenHeight+40);
					}
				}
			}
			draw();

		}
	}
	
	public void draw() {
		Canvas canvas;
		if ((canvas = surface.lockCanvas()) != null) {
	        canvas.drawColor(Color.parseColor("#EEEEEE"));
	        for (AndroObject a: objects) {
	        	a.draw(canvas);
	        }
	        surface.unlockCanvasAndPost(canvas);
		}
	}
	
	public void touchEvent(int x, int y) {
		boolean dont = false;
		for (AndroObject a: objects) {
			if (Math.abs(a.getX()-x)<40 && Math.abs(a.getY()-y)<40) {
				selected = a;
				dont = true;
			}
        }
		if (!dont) {
			AndroObject a = new AndroObject();
			a.setPosition(x, y);
			objects.add(a);
		}
		
	}

}
