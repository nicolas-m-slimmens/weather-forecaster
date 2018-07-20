package org.slimmens.weather.forecaster.service.controllers;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.slimmens.weather.forecaster.model.entities.WeatherPrediction;
import org.slimmens.weather.forecaster.model.repositories.WeatherPredictionsRepository;
import org.slimmens.weather.forecaster.service.exceptions.BadRequestException;
import org.slimmens.weather.forecaster.service.exceptions.NotFoundException;
import org.slimmens.weather.forecaster.service.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/clima")
public class WeatherPredictionsController {

	@Log
	private Logger log;

	@Autowired
	private WeatherPredictionsRepository weatherPredictionsRepository;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public WeatherPrediction getWeatherPrediction(@RequestParam("dia") long day) {
		log.info(String.format("Getting the weather prediction for the day %d...", day));
		
		if (day < 0) {
			String message = "The required day must not be negative.";
			log.error(message);
			throw new BadRequestException(message);
		}

		Optional<WeatherPrediction> weatherPrediction = weatherPredictionsRepository.findById(day);
		if (!weatherPrediction.isPresent()) {
			String message = "Unable to find a prediction for the given day.";
			log.error(message);
			throw new NotFoundException(message);
		}

		return weatherPrediction.get();
	}

}
