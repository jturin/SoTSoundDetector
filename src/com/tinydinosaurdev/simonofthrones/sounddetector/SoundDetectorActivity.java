package com.tinydinosaurdev.simonofthrones.sounddetector;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

public class SoundDetectorActivity extends ActionBarActivity {

	private boolean detecting = false;
	private AudioDispatcher dispatcher;
	private PitchDetectionHandler detectionHandler;
	private PitchProcessor pitchProcessor;
	private Handler handler;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		detecting = false;
		setContentView(R.layout.activity_sound_detector);
		textView = (TextView) findViewById(R.id.textView1);
		textView.setText("Sound disabled");

		handler = new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(Message inputMessage) {
				textView.setText(((SOUND) inputMessage.obj).toString());
			}
		};

		detectionHandler = new SoundDetectionHandler(handler);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sound_detector, menu);
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

	public void startDetection(View notUsed) {
		Log.d(this.getLocalClassName(), "Detecting");
		if (detecting == true) {
			return;
		}
		dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024,
				0);
		pitchProcessor = new PitchProcessor(PitchEstimationAlgorithm.FFT_YIN,
				22050, 1024, detectionHandler);

		new Thread(dispatcher, "Audio Dispatcher").start();
		dispatcher
				.addAudioProcessor(new PitchProcessor(
						PitchEstimationAlgorithm.FFT_YIN, 22050, 1024,
						detectionHandler));
		detecting = true;
	}

	public void stopDetection(View notUsed) {
		Log.d(this.getLocalClassName(), "Stop Detecting");
		if (detecting == false) {
			return;
		}
		dispatcher.removeAudioProcessor(pitchProcessor);
		dispatcher.stop();
		textView.setText("Sound disabled");
		detecting = false;
	}
}
