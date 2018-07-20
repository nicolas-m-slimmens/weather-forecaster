package org.slimmens.weather.forecaster.core;

import org.slimmens.weather.forecaster.core.math.Point;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * A planet part of a solar system, which moves on a specific {@link Orbit}
 * path.
 * 
 * @author Nicolás Martín
 */
public class Planet {

	/**
	 * The name of this <code>Planet</code>.
	 */
	private final String name;

	/**
	 * The current position of this <code>Planet</code>.
	 */
	private Point position;

	/**
	 * The orbit that this <code>Planet</code> follows when moving.
	 */
	private final Orbit orbit;

	/**
	 * Constructs and initializes a planet with the given name and orbit.
	 * 
	 * @param name
	 *            a {@link String} with the name of the planet. The name must
	 *            not be <code>null</code>.
	 * @param y
	 *            a {@link Orbit} representing the orbit that the planet follows
	 *            when moving. The orbit must not be <code>null</code>.
	 */
	public Planet(String name, Orbit orbit) {
		super();

		Assert.isTrue(!StringUtils.isEmpty(name), "A name is required to create a new planet.");
		Assert.notNull(orbit, "An orbit is required to create a new planet.");

		this.name = name;
		this.position = orbit.getInitialPosition();
		this.orbit = orbit;
	}

	/**
	 * Changes the current position of the planet to the next one obtained from
	 * the <code>Orbit</code>.
	 */
	public void moveFoward() {
		this.position = this.orbit.getNextPosition(this.position);
	}

	/**
	 * Changes the current position of the planet to the previous one obtained
	 * from the <code>Orbit</code>.
	 */
	public void moveBackward() {
		this.position = this.orbit.getPreviousPosition(this.position);
	}

	/**
	 * Returns the position where this <code>Planet</code> will be on the
	 * following day.
	 * 
	 * @return a {@link Point} representing the position of this
	 *         <code>Planet</code> for the following day.
	 */
	public Point getNextPosition() {
		return this.orbit.getNextPosition(this.position);
	}

	/**
	 * Returns the position where this <code>Planet</code> was on the previous
	 * day.
	 * 
	 * @return a {@link Point} representing the position of this
	 *         <code>Planet</code> for the previous day.
	 */
	public Point getPreviousPosition() {
		return this.orbit.getPreviousPosition(this.position);
	}

	public String getName() {
		return name;
	}

	public Point getPosition() {
		return position;
	}

	public Orbit getOrbit() {
		return orbit;
	}

	@Override
	public String toString() {
		return "Planet [name=" + name + ", position=" + position + ", orbit=" + orbit + "]";
	}

}
