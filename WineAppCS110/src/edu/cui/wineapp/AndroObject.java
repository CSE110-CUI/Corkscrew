package edu.cui.wineapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import edu.cui.wineapp.views.AndroObjectView;

public class AndroObject
{	
	private Point position;
	
	public void move() {
		position.y = position.y-1;
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(AndroObjectView.bitmap, position.x-AndroObjectView.bitmap.getWidth()/2, position.y-AndroObjectView.bitmap.getHeight()/2, new Paint());
		// canvas.drawCircle(position.x, position.y, 40, new Paint());
	}
	
	public void setPosition(int x, int y) {
		position = new Point(x, y);
	}

	public int getX() {
		return position.x;
	}
	
	public int getY() {
		return position.y;
	}
}
