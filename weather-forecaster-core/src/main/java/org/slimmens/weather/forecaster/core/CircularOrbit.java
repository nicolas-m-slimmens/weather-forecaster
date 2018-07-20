package org.slimmens.weather.forecaster.core;

import java.math.BigDecimal;

import org.slimmens.weather.forecaster.core.math.Angle;
import org.slimmens.weather.forecaster.core.math.Point;
import org.springframework.util.Assert;

/**
 * {@link Orbit} implementation that represents a circular path with fixed
 * displacement angle and distance to the star.
 *
 * @author Nicolás Martín
 */

public class CircularOrbit implements Orbit {

	/**
	 * The distance of the orbit path to the star.
	 */
	private final BigDecimal starDistance;

	/**
	 * The displacement measured in degrees that a <code>Planet</code> in this
	 * <code>Orbit</code> will achieve during one day.
	 */
	private final Angle angularDisplacement;

	/**
	 * Constructs and initializes a circular orbit with the star distance and
	 * angular displacement.
	 * 
	 * @param starDistance
	 *            a {@link BigDecimal} representing the distance of the orbit
	 *            path to the star. The distance must not be <code>null</code>.
	 * @param angularDisplacement
	 *            an {@link Angle} representing the displacement of a planet in
	 *            this <code>Orbit</code> during one day. The angular
	 *            displacement must not be <code>null</code>.
	 */
	public CircularOrbit(BigDecimal starDistance, Angle angularDisplacement) {
		super();

		Assert.notNull(starDistance, "A distance to the star is required to create a new circular orbit.");
		Assert.notNull(angularDisplacement, "An angular displacement is required to create a new circular orbit.");

		this.starDistance = starDistance;
		this.angularDisplacement = angularDisplacement;
	}

	/**
	 * Returns the initial position of a planet in this <code>Orbit</code>,
	 * which will be at 00° 00' 00" of rotation and the previously configured
	 * distance from the star.
	 * 
	 * @return a {@link Point} representing the initial position of a planet in
	 *         this orbit.
	 */
	@Override
	public Point getInitialPosition() {
		return new Point(starDistance, new Angle());
	}

	/**
	 * Returns position for the next day based on the given position of the
	 * planet in this <code>Orbit</code>. The returned position will be
	 * calculated adding the fixed <code>angularDisplacement</code> to the given
	 * position.
	 * 
	 * @param currentPosition
	 *            a {@link Point} representing the current position of a planet
	 *            in this orbit.
	 * @return a {@link Point} representing the exact next position of a planet
	 *         in this orbit.
	 */
	@Override
	public Point getNextPosition(final Point currentPosition) {
		Angle newAngle = currentPosition.getAngle().add(angularDisplacement);
		return new Point(this.starDistance, newAngle);
	}

	/**
	 * Returns position for the next day based on the given position of the
	 * planet in this <code>Orbit</code>. The returned position will be
	 * calculated adding the fixed <code>angularDisplacement</code> to the given
	 * position.
	 * 
	 * @param currentPosition
	 *            a {@link Point} representing the current position of a planet
	 *            in this orbit.
	 * @return a {@link Point} representing the exact next position of a planet
	 *         in this orbit.
	 */
	@Override
	public Point getPreviousPosition(final Point currentPosition) {
		Angle newAngle = currentPosition.getAngle().substract(angularDisplacement);
		return new Point(this.starDistance, newAngle);
	}

	public BigDecimal getStarDistance() {
		return starDistance;
	}

	public Angle getAngularDisplacement() {
		return angularDisplacement;
	}

	@Override
	public String toString() {
		return "CircularOrbit [starDistance=" + starDistance + ", angularDisplacement=" + angularDisplacement + "]";
	}

}
