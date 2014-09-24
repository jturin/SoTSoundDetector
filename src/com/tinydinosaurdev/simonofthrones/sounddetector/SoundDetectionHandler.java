package com.tinydinosaurdev.simonofthrones.sounddetector;

import android.os.Handler;
import android.os.Message;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;

public class SoundDetectionHandler implements PitchDetectionHandler {

	private final Handler handler;

	public SoundDetectionHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void handlePitch(PitchDetectionResult pitchDetectionResult,
			AudioEvent audioEvent) {
		final float pitchInHz = pitchDetectionResult.getPitch();
		SOUND sound = SOUND.getSoundForFrequency(pitchInHz);

		Message m = Message.obtain();
		m.obj = sound;

		handler.sendMessage(m);

	}

}
