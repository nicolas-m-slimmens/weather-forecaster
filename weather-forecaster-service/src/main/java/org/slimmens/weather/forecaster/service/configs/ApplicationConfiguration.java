package org.slimmens.weather.forecaster.service.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ PersistenceConfiguration.class, WebConfiguration.class })
public class ApplicationConfiguration {

}
