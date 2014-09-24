package com.tinydinosaurdev.simonofthrones.sounddetector;


public enum SOUND {

	NO_SOUND(0f), LA(27.50f), RE(18.35f), FA(21.83f), SOL(24.50f);
	private static final float DO = 16.35f;
	private static final int NOOB_LEVEL = 1;

	private float baseFrequency;

	private SOUND(float frequency) {
		this.baseFrequency = frequency;
	}

	public float getBaseFrequency() {
		return baseFrequency;
	}

	public static SOUND getSoundForFrequency(float frequency) {
		if (frequency <= 0) {
			// Love safe checks
			return NO_SOUND;
		}

		int scale = getScale(frequency, 0);

		if (checkFrequencyWithNoobLevel(frequency, RE, scale)) {
			return RE;
		} else if (checkFrequencyWithNoobLevel(frequency, FA, scale)) {
			return FA;
		} else if (checkFrequencyWithNoobLevel(frequency, SOL, scale)) {
			return SOL;
		} else if (checkFrequencyWithNoobLevel(frequency, LA, scale)) {
			return LA;
		}

		return NO_SOUND;

	}

	private static boolean checkFrequencyWithNoobLevel(float frequency,
			SOUND sound, int scale) {
		double multiplier = Math.pow(2, scale);
		return (((multiplier * (sound.getBaseFrequency() - NOOB_LEVEL)) <= frequency) && (multiplier * (sound
				.getBaseFrequency() + NOOB_LEVEL)) >= frequency);
	}

	private static int getScale(float frequency, int currentScale) {

		if (frequency < DO * Math.pow(2, currentScale + 1)) {
			return currentScale;
		}
		currentScale++;
		return getScale(frequency, currentScale);

	}

}

