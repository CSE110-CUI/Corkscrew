package edu.cui.wineapp.views;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.cui.wineapp.FizzGameUIThread;
import edu.cui.wineapp.R;
import edu.cui.wineapp.Sizes;

public class AndroObjectView extends SurfaceView implements SurfaceHolder.Callback {
	
	public static Bitmap bitmap;
	public MediaPlayer mp;
	public int dice;
	
	public FizzGameUIThread thread;
	
	public AndroObjectView(Context context) {
		super(context);
		getHolder().addCallback(this);
		thread = new FizzGameUIThread(getHolder());
		Log.e("inside", "inside");
		dice = new Random().nextInt(6);
		switch(dice)
		{
		case 0:
			mp = MediaPlayer.create(context, R.raw.party);
			break;
		case 1:
			mp = MediaPlayer.create(context, R.raw.woo);
			break;
		case 2:
			mp = MediaPlayer.create(context, R.raw.woohoo);
			break;
		case 3:
			mp = MediaPlayer.create(context, R.raw.yeah);
			break;
		case 4:
			mp = MediaPlayer.create(context, R.raw.woohoo);
		case 5:
			mp = MediaPlayer.create(context, R.raw.yeah_ha_ha_ha);
			break;
		default:
			break;
		}
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gillsp);
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
    		if (getHolder().getSurface().isValid()) {
		    	thread.touchEvent((int)event.getX(), (int)event.getY());
				mp.start();
				dice = new Random().nextInt(6);
				Log.e(""+dice, ""+dice);
				switch(dice)
				{
				case 0:
					mp = MediaPlayer.create(getContext(), R.raw.party);
					break;
				case 1:
					mp = MediaPlayer.create(getContext(), R.raw.woo);
					break;
				case 2:
					mp = MediaPlayer.create(getContext(), R.raw.woohoo);
					break;
				case 3:
					mp = MediaPlayer.create(getContext(), R.raw.yeah);
					break;
				case 4:
					mp = MediaPlayer.create(getContext(), R.raw.woohoo);
					break;
				case 5:
					mp = MediaPlayer.create(getContext(), R.raw.yeah_ha_ha_ha);
				default:
					break;
				}
    		}
        }
        return false;
    }
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Sizes.screenWidth = holder.getSurfaceFrame().width();
		Sizes.screenHeight = holder.getSurfaceFrame().height();
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}
}
