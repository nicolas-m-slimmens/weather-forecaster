package org.slimmens.weather.forecaster.core;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slimmens.weather.forecaster.core.math.Point;
import org.slimmens.weather.forecaster.core.math.Polygon;
import org.springframework.util.Assert;

/**
 * A useful helper class to calculate the weather condition of a solar system.
 * 
 * @author Nicolás Martín
 */
public class WeatherForecaster {

	/**
	 * Returns a weather condition of a solar system represented by a star and an
	 * array of planets. The method will calculate the weather condition based on
	 * the planets positions.
	 * 
	 * <table border>
	 * <tr valign=top>
	 * <th>Weather Condition</th>
	 * <th>Requirement</th>
	 * </tr>
	 * <tr align=left>
	 * <td>OPTIMAL_PRESSURE_AND_TEMP</td>
	 * <td>The planets are aligned with each other.</td>
	 * </tr>
	 * <tr align=left>
	 * <td>DROUGHT</td>
	 * <td>The planets are aligned with each other and with the sun.</td>
	 * </tr>
	 * <tr align=left>
	 * <td>RAIN</td>
	 * <td>The planets form a polygon that contains the sun.</td>
	 * </tr>
	 * <tr align=left>
	 * <td>RAIN_PICK</td>
	 * <td>The planets form a polygon that contains the sun, and the perimeter of
	 * that polygon is the largest in the rainy season.</td>
	 * </tr>
	 * <tr align=left>
	 * <td>NONE</td>
	 * <td>No of the previous conditions matches, so not weather condition was
	 * determinated.</td>
	 * </tr>
	 * </table>
	 * 
	 * @param starPosition
	 *            a {@link Point} representing the position of the star of the solar
	 *            system.
	 * @param planets
	 *            an array of {@link Planet} that are part of the solar system.
	 * @return the {@link WeatherCondition} for that specific star and planets
	 *         positions.
	 */
	public static WeatherCondition getWeatherCondition(final Point starPosition, final Planet... planets) {
		Assert.notNull(starPosition, "A star location is required to get the weather condition.");
		Assert.notNull(planets, "At least one planet is required to get the weather condition.");

		List<Point> planetPositions = Arrays.stream(planets).map(planet -> planet.getPosition())
				.collect(Collectors.toList());

		Polygon polygon = new Polygon(planetPositions);

		// If the polygon area is empty, it is a line. So the planets are align.
		if (polygon.isEmpty()) {
			polygon.addPoint(starPosition);

			// If the area of the polygon still being zero when adding the sun,
			// then the system has drought.
			if (polygon.isEmpty()) {
				return WeatherCondition.DROUGHT;
			}

			// If not, just the planets are align, so the system has optimal
			// pressure and temp.
			return WeatherCondition.OPTIMAL_PRESSURE_AND_TEMP;
		} else if (polygon.contains(starPosition)) {

			// If the sun is contained in the polygon and the perimeter is the
			// maximum in the rain season, the system
			// has a rain pick.
			if (hasReachedMaximumPerimeter(polygon.getPerimeter(), planets)) {
				return WeatherCondition.RAIN_PICK;
			}

			// If not, the system has just a common rain.
			return WeatherCondition.RAIN;
		}

		// For other cases, there is no weather forecast.
		return WeatherCondition.NONE;
	}

	private static boolean hasReachedMaximumPerimeter(BigDecimal perimeter, Planet... planets) {
		List<Point> planetPreviousPositions = Arrays.stream(planets).map(planet -> planet.getPreviousPosition())
				.collect(Collectors.toList());

		BigDecimal previousPerimeter = (new Polygon(planetPreviousPositions)).getPerimeter();
		if (perimeter.compareTo(previousPerimeter) < 0) {
			return false;
		}

		List<Point> planetNextPositions = Arrays.stream(planets).map(planet -> planet.getNextPosition())
				.collect(Collectors.toList());

		BigDecimal nextPerimeter = (new Polygon(planetNextPositions)).getPerimeter();
		if (perimeter.compareTo(nextPerimeter) < 0) {
			return false;
		}

		return true;
	}

}
