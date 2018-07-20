package org.slimmens.weather.forecaster.core.math;

import java.math.MathContext;
import java.math.RoundingMode;

public class MathConstants {

	public static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_DOWN;
	
	public static final int DEFAULT_SCALE = 3;
	
	public static final int DEFAULT_PRECISION = 16;
	
	public static final MathContext DEFAULT_CONTEXT = new MathContext(DEFAULT_PRECISION, DEFAULT_ROUNDING);

	/*
	 * This class is just to store math related constants and must not be
	 * initialized.
	 */
	private MathConstants() {

	}

}
