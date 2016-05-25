package com.echengguo.handlertest;

import android.app.Activity;
import android.media.tv.TvContract.Programs;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	final String TAG = "handler";
	ProgressBar bar;
	ImageView iview;
	int curr_id = 0;
	final int INC = 1;
	final int DEC = 2;
	final int SWITCH_CYCLE = 3;
	Button bPrev;
	Button bNext;
	Button autoplay;
	int [] pic_id = {
		R.drawable.tst1,
		R.drawable.tst2,
		R.drawable.tst3,
		R.drawable.tst4,
		R.drawable.tst5,
		R.drawable.tst6,
		R.drawable.tst7,
		R.drawable.tst8
	};
	
	boolean Is_running = false;
	private View.OnClickListener MyListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == bPrev)
			{
				curr_id = (curr_id + 1) % pic_id.length;
				iview.setImageResource(pic_id[curr_id]);
			}
			else if(v == bNext)
			{
				curr_id = (curr_id - 1 + pic_id.length) % pic_id.length;
				iview.setImageResource(pic_id[curr_id]);
			}
			else if(v == autoplay)
			{
				Action();
			}
		}
	};
	
	private void Action()
	{
		Thread handleBarThread = new Thread(new Runnable() {
			public void run() {
				
				for(; ;)
				{
					try {
						Message msg = new Message();
						msg.what = SWITCH_CYCLE;
						handler.sendMessage(msg);
						Log.i(TAG, "Thread id " + Thread.currentThread().getId() + ",sendmessage SWITCH_CYCLE" );
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				

			}
		});
		Is_running = true;
		handleBarThread.start();
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what) {
			case INC:
				bar.incrementProgressBy(5);
				Log.i(TAG, "Thread id" +Thread.currentThread().getId() +", handler");
				break;
			case DEC:
				bar.incrementProgressBy(-5);
				Log.i(TAG, "Thread id" + Thread.currentThread().getId() + ", handler");
				break;
			case SWITCH_CYCLE:
				int picid = (curr_id++) % pic_id.length;
				iview.setImageResource(pic_id[picid]);
				break;
			default:
				Log.i(TAG, "Thread id" + Thread.currentThread().getId() + ", handlder");
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bar = (ProgressBar)findViewById(R.id.progress);
		iview = (ImageView)findViewById(R.id.iv);
		iview.setImageResource(R.drawable.tst10);
		bPrev = (Button)findViewById(R.id.previous);
		bNext = (Button)findViewById(R.id.next);
		bPrev.setOnClickListener(MyListener);
		bNext.setOnClickListener(MyListener);
		autoplay = (Button)findViewById(R.id.play);
		autoplay.setOnClickListener(MyListener);
	}
	
	protected void onStart() {
		super.onStart();
		bar.setProgress(0);
	/*	Thread handleBarThread = new Thread(new Runnable() {
			public void run() {
				
				for(int i = 0; i < 20 && Is_running; i++)
				{
					try {
						Message msg = new Message();
						msg.what = SWITCH_CYCLE;
						handler.sendMessage(msg);
						Log.i(TAG, "Thread id " + Thread.currentThread().getId() + ",sendmessage SWITCH_CYCLE" );
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				

			}
		});
		Is_running = true;
		handleBarThread.start();*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
