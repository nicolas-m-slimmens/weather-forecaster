package org.slimmens.weather.forecaster.service.jobs;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.slimmens.weather.forecaster.core.CircularOrbit;
import org.slimmens.weather.forecaster.core.Planet;
import org.slimmens.weather.forecaster.core.WeatherCondition;
import org.slimmens.weather.forecaster.core.WeatherForecaster;
import org.slimmens.weather.forecaster.core.math.Angle;
import org.slimmens.weather.forecaster.core.math.Point;
import org.slimmens.weather.forecaster.model.entities.WeatherPrediction;
import org.slimmens.weather.forecaster.model.repositories.WeatherPredictionsRepository;
import org.slimmens.weather.forecaster.service.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializationJob {

	private static final Point SUN_POSITION = new Point();

	private static final BigDecimal DISTANCE_PLANET_FERENGIS = new BigDecimal(500);
	private static final BigDecimal DISTANCE_PLANET_BETASOIDE = new BigDecimal(2000);
	private static final BigDecimal DISTANCE_PLANET_VULCANO = new BigDecimal(1000);

	private static final Angle ANGULAR_DISPLACEMENT_PLANET_FERENGIS = new Angle(-1);
	private static final Angle ANGULAR_DISPLACEMENT_PLANET_BETASOIDE = new Angle(-3);
	private static final Angle ANGULAR_DISPLACEMENT_PLANET_VULCANO = new Angle(5);
	
	@Autowired
	private WeatherPredictionsRepository weatherPredictionsRepository;

	@PostConstruct
	@Transactional
	public void run() {
		Planet planetFerengis = new Planet("Ferengis",
				new CircularOrbit(DISTANCE_PLANET_FERENGIS, ANGULAR_DISPLACEMENT_PLANET_FERENGIS));
		Planet planetBetasoide = new Planet("Betasoide",
				new CircularOrbit(DISTANCE_PLANET_BETASOIDE, ANGULAR_DISPLACEMENT_PLANET_BETASOIDE));
		Planet planetVulcano = new Planet("Vulcano",
				new CircularOrbit(DISTANCE_PLANET_VULCANO, ANGULAR_DISPLACEMENT_PLANET_VULCANO));

		long day = 0;

		LocalDate currentDate = LocalDate.now();
		LocalDate endDate = currentDate.plusYears(10);
		while (!currentDate.isAfter(endDate)) {
			WeatherCondition condition = WeatherForecaster.getWeatherCondition(SUN_POSITION, planetFerengis,
					planetBetasoide, planetVulcano);

			WeatherPrediction weatherPrediction = new WeatherPrediction();
			weatherPrediction.setDay(day++);
			weatherPrediction.setCondition(condition);
			weatherPredictionsRepository.save(weatherPrediction);
			
			planetFerengis.moveFoward();
			planetBetasoide.moveFoward();
			planetVulcano.moveFoward();
			currentDate = currentDate.plusDays(1);
		}
	}

}
