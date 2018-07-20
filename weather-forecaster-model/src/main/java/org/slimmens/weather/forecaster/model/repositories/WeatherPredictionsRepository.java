package org.slimmens.weather.forecaster.model.repositories;

import org.slimmens.weather.forecaster.model.entities.WeatherPrediction;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WeatherPredictionsRepository extends PagingAndSortingRepository<WeatherPrediction, Long> {

}
