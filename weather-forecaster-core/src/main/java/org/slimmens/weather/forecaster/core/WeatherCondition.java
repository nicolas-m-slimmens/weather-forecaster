package org.slimmens.weather.forecaster.core;

/**
 * Specify a weather condition for a given planet.
 * 
 * @author Nicolás Martín
 */
public enum WeatherCondition {

	NONE("None"), RAIN("Rain"), RAIN_PICK("Rain Pick"), DROUGHT("Drought"), OPTIMAL_PRESSURE_AND_TEMP(
			"Optimal Pressure and Temperature");

	/*
	 * The name of this weather condition.
	 */
	private String name;

	private WeatherCondition(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
