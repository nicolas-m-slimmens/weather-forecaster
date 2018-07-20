package org.slimmens.weather.forecaster.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slimmens.weather.forecaster.core.math.Angle;
import org.slimmens.weather.forecaster.core.math.Point;

public class Main {

	private static final Point SUN_POSITION = new Point();

	private static final BigDecimal DISTANCE_PLANET_FERENGIS = new BigDecimal(500);
	private static final BigDecimal DISTANCE_PLANET_BETASOIDE = new BigDecimal(2000);
	private static final BigDecimal DISTANCE_PLANET_VULCANO = new BigDecimal(1000);

	private static final Angle ANGULAR_DISPLACEMENT_PLANET_FERENGIS = new Angle(-1);
	private static final Angle ANGULAR_DISPLACEMENT_PLANET_BETASOIDE = new Angle(-3);
	private static final Angle ANGULAR_DISPLACEMENT_PLANET_VULCANO = new Angle(5);

	public static void main(String[] args) {
		Planet planetFerengis = new Planet("Ferengis",
				new CircularOrbit(DISTANCE_PLANET_FERENGIS, ANGULAR_DISPLACEMENT_PLANET_FERENGIS));
		Planet planetBetasoide = new Planet("Betasoide",
				new CircularOrbit(DISTANCE_PLANET_BETASOIDE, ANGULAR_DISPLACEMENT_PLANET_BETASOIDE));
		Planet planetVulcano = new Planet("Vulcano",
				new CircularOrbit(DISTANCE_PLANET_VULCANO, ANGULAR_DISPLACEMENT_PLANET_VULCANO));

		int droughtSeasonCounter = 0;
		int rainSeasonCounter = 0;
		int optimalConditionsSeasonCounter = 0;
		List<LocalDate> rainPickDays = new ArrayList<>();
		
		WeatherCondition seasonCondition = WeatherCondition.NONE;
		
		LocalDate currentDate = LocalDate.now();
		LocalDate endDate = currentDate.plusYears(10);
		while (!currentDate.isAfter(endDate)) {
			WeatherCondition currentCondition = WeatherForecaster.getWeatherCondition(SUN_POSITION, planetFerengis, planetBetasoide,
					planetVulcano);
			
			planetFerengis.moveFoward();
			planetBetasoide.moveFoward();
			planetVulcano.moveFoward();
			currentDate = currentDate.plusDays(1);

			if (currentCondition == seasonCondition) {
				continue;
			}
			
			if (currentCondition == WeatherCondition.RAIN_PICK) {
				rainPickDays.add(currentDate);
				continue;
			}
			
			seasonCondition = currentCondition;
			
			switch (currentCondition) {
			case DROUGHT:
				droughtSeasonCounter++;
				break;
			case RAIN:
				rainSeasonCounter++;
				break;
			case OPTIMAL_PRESSURE_AND_TEMP:
				optimalConditionsSeasonCounter++;
				break;
			default:
				break;
			}
		}
		
		System.out.println(String.format("Drought Seasons: %d", droughtSeasonCounter));
		System.out.println(String.format("Rain Seasons: %d", rainSeasonCounter));
		System.out.println(String.format("Optimal Conditions Seasons: %d", optimalConditionsSeasonCounter));
		System.out.println(String.format("Rain picks: %s", StringUtils.join(rainPickDays, ", ")));

	}

}
