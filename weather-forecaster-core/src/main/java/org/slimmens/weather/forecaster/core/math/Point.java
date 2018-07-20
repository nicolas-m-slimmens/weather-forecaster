package org.slimmens.weather.forecaster.core.math;

import java.math.BigDecimal;

import org.springframework.util.Assert;

import ch.obermuhlner.math.big.BigDecimalMath;

/**
 * A point in the space represented both by its polar coordinates and by its
 * Cartesian coordinates.
 * 
 * @author Nicolás Martín
 */
public class Point {

	/**
	 * The X Cartesian coordinate component of this <code>Point</code>.
	 */
	private final BigDecimal x;

	/**
	 * The Y Cartesian coordinate component of this <code>Point</code>.
	 */
	private final BigDecimal y;

	/**
	 * The distance of this <code>Point</code> to the origin.
	 */
	private final BigDecimal distance;

	/**
	 * The angle of this <code>Point</code> from the reference direction.
	 */
	private final Angle angle;

	/**
	 * Constructs and initializes a point at the origin.
	 */
	public Point() {
		super();
		this.x = BigDecimal.ZERO;
		this.y = BigDecimal.ZERO;
		this.distance = BigDecimal.ZERO;
		this.angle = new Angle();
	}

	/**
	 * Constructs and initializes a point with the given {@code (x,y)}
	 * coordinates in the Cartesian system.
	 * 
	 * @param x
	 *            a {@link BigDecimal} representing the X component in the
	 *            Cartesian system. The component must not be <code>null</code>.
	 * @param y
	 *            a {@link BigDecimal} representing the Y component in the
	 *            Cartesian system. The component must not be <code>null</code>.
	 */
	public Point(BigDecimal x, BigDecimal y) {
		super();

		Assert.notNull(x, "A coordinate X is require to create a new point.");
		Assert.notNull(y, "A coordinate Y is require to create a new point.");
		this.x = x;
		this.y = y;

		this.distance = BigDecimalMath.sqrt(x.pow(2).add(y.pow(2)), MathConstants.DEFAULT_CONTEXT);
		this.angle = null; // TODO
	}

	/**
	 * Constructs and initializes a point with the given {@code (r,t)}
	 * coordinates in the polar system.
	 * 
	 * @param distance
	 *            a {@link BigDecimal} representing the distance to the origin.
	 *            The distance must not be <code>null</code>.
	 * @param angle
	 *            an {@link Angle} from the reference direction. The angle must
	 *            not be <code>null</code>.
	 */
	public Point(BigDecimal distance, Angle angle) {
		super();

		Assert.notNull(distance, "A distance from the origin is require to create a new point.");
		Assert.notNull(angle, "An angle is require to create a new point.");
		this.distance = distance;
		this.angle = angle;

		double radians = Math.toRadians(angle.toDouble());
		this.x = distance.multiply(new BigDecimal(Math.cos(radians))).setScale(MathConstants.DEFAULT_SCALE,
				MathConstants.DEFAULT_ROUNDING);
		this.y = distance.multiply(new BigDecimal(Math.sin(radians))).setScale(MathConstants.DEFAULT_SCALE,
				MathConstants.DEFAULT_ROUNDING);
	}

	/**
	 * Check if this point is at the left of the given point.
	 * 
	 * @return <code>true</code> if this <code>Point</code> has its component X
	 *         less than the given <code>Point</code> and <code>false</code> if
	 *         not.
	 */
	public boolean isOnLeft(Point point) {
		return this.getX().compareTo(point.getX()) < 0;
	}

	/**
	 * Check if this point is at the right of the given point.
	 * 
	 * @return <code>true</code> if this <code>Point</code> has its component X
	 *         greater than the given <code>Point</code> and <code>false</code>
	 *         if not.
	 */
	public boolean isOnRight(Point point) {
		return this.getX().compareTo(point.getX()) > 0;
	}

	/**
	 * Check if this point is above the given point.
	 * 
	 * @return <code>true</code> if this <code>Point</code> has its component Y
	 *         greater than the given <code>Point</code> and <code>false</code>
	 *         if not.
	 */
	public boolean isAbove(Point point) {
		return this.getY().compareTo(point.getY()) > 0;
	}

	/**
	 * Check if this point is under the given point.
	 * 
	 * @return <code>true</code> if this <code>Point</code> has its component Y
	 *         less than the given <code>Point</code> and <code>false</code> if
	 *         not.
	 */
	public boolean isUnder(Point point) {
		return this.getY().compareTo(point.getY()) < 0;
	}

	/**
	 * Check if this point vertically aligned to the given point.
	 * 
	 * @return <code>true</code> if this <code>Point</code> has its component X
	 *         equals to the given <code>Point</code> and <code>false</code> if
	 *         not.
	 */
	public boolean isVerticallyAlign(Point point) {
		return this.getX().compareTo(point.getX()) == 0;
	}

	/**
	 * Check if this point horizontally aligned the given point.
	 * 
	 * @return <code>true</code> if this <code>Point</code> has its component Y
	 *         equals to the given <code>Point</code> and <code>false</code> if
	 *         not.
	 */
	public boolean isHorizontallyAlign(Point point) {
		return this.getY().compareTo(point.getY()) == 0;
	}

	public BigDecimal getX() {
		return x;
	}

	public BigDecimal getY() {
		return y;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public Angle getAngle() {
		return angle;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", distance=" + distance + ", angle=" + angle + "]";
	}

}
