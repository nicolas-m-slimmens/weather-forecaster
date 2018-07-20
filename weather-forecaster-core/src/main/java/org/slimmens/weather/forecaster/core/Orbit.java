package org.slimmens.weather.forecaster.core;

import org.slimmens.weather.forecaster.core.math.Point;

/**
 * An interface used by {@link Planet} to get the initial and next position.
 * Implementations of this interface perform the actual work of calculating the
 * start and next {@link Point} representing the planet position.
 * 
 * @author Nicolás Martín
 */
public interface Orbit {

	/**
	 * Implementations must implement this method to get the initial position of
	 * a planet in this <code>Orbit</code>.
	 * 
	 * @return a {@link Point} representing the initial position of a planet in
	 *         this <code>Orbit</code>.
	 */
	public Point getInitialPosition();

	/**
	 * Implementations must implement this method to calculate position for the
	 * next day based on the current position of a planet in this <code>Orbit</code>.
	 * 
	 * @param currentPosition
	 *            a {@link Point} representing the current position of a planet
	 *            in this <code>Orbit</code>.
	 * @return a {@link Point} representing the exact next position of a planet
	 *         in this <code>Orbit</code>.
	 */
	public Point getNextPosition(final Point currentPosition);
	
	/**
	 * Implementations must implement this method to calculate position for the
	 * previous day based on the current position of a planet in this <code>Orbit</code>.
	 * 
	 * @param currentPosition
	 *            a {@link Point} representing the current position of a planet
	 *            in this <code>Orbit</code>.
	 * @return a {@link Point} representing the exact previous position of a planet
	 *         in this <code>Orbit</code>.
	 */
	public Point getPreviousPosition(final Point currentPosition);

}
