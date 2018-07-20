package org.slimmens.weather.forecaster.core.math;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.Assert;

import ch.obermuhlner.math.big.BigDecimalMath;

/**
 * A polygon represented by a list of points in the space.
 * 
 * @author Nicolás Martín
 */
public class Polygon {

	/**
	 * The list of points that represents the edges of this
	 * <code>Polygon</code>.
	 */
	private List<Point> points;

	/**
	 * Constructs and initializes a polygon with the given points as it edges.
	 * 
	 * @param points
	 *            a {@link List} of the edges of the newly constructed
	 *            <code>Polygon</code>. The list should be in order and must not
	 *            be <code>null</code>.
	 */
	public Polygon(List<Point> points) {
		super();

		Assert.notNull(points, "A list of points is required to create a new polygon.");
		this.points = points;
	}

	/**
	 * Returns the sum of the sides of the polygon.
	 * 
	 * @return a {@link BigDecimal} representing the perimeter of this
	 *         <code>Polygon</code>
	 */
	public BigDecimal getPerimeter() {
		BigDecimal perimeter = BigDecimal.ZERO;
		int j = points.size() - 1;

		for (int i = 0; i < points.size(); i++) {
			Point firstPoint = points.get(j);
			Point secondPoint = points.get(i);

			BigDecimal componentX = (firstPoint.getX().subtract(secondPoint.getX())).pow(2);
			BigDecimal componentY = (firstPoint.getY().subtract(secondPoint.getY())).pow(2);
			perimeter = perimeter.add(BigDecimalMath.sqrt(componentX.add(componentY), MathConstants.DEFAULT_CONTEXT));

			j = i;
		}

		return perimeter;
	}

	/**
	 * Returns the area contained by the sides of the polygon.
	 * 
	 * @return a {@link BigDecimal} representing the area of this
	 *         <code>Polygon</code>
	 */
	public BigDecimal getArea() {
		BigDecimal area = BigDecimal.ZERO;
		int j = points.size() - 1;

		for (int i = 0; i < points.size(); i++) {
			Point firstPoint = points.get(j);
			Point secondPoint = points.get(i);

			BigDecimal xAddition = firstPoint.getX().add(secondPoint.getX());
			BigDecimal ySubstraction = firstPoint.getY().subtract(secondPoint.getY());
			area = area.add(xAddition.multiply(ySubstraction));

			j = i;
		}

		return area.divide(new BigDecimal(2)).abs();
	}

	/**
	 * Check if this polygon has area.
	 * 
	 * @return <code>true</code> if this <code>Polygon</code> has area greater
	 *         than zero and <code>false</code> if not.
	 */
	public boolean isEmpty() {
		return BigDecimal.ZERO.compareTo(this.getArea()) == 0;
	}

	/**
	 * Check if the given <code>Point</code> is contained by this polygon.
	 * 
	 * @param point
	 *            a {@link Point} to check if is contained in this
	 *            <code>Polygon<code>.
	 * @return <code>true</code> if this <code>Polygon</code> contains the given
	 *         <code>Point</code> and <code>false</code> if not.
	 */
	public boolean contains(Point point) {
		boolean inside = false;
		int j = points.size() - 1;

		for (int i = 0; i < points.size(); i++) {
			Point firstPoint = points.get(j);
			Point secondPoint = points.get(i);

			if (intersects(firstPoint, secondPoint, point)) {
				inside = !inside;
			}

			j = i;
		}

		return inside;
	}

	private boolean intersects(Point firstPoint, Point secondPoint, Point testPoint) {
		if (firstPoint.isAbove(testPoint) == secondPoint.isAbove(testPoint)) {
			return false;
		}

		BigDecimal firstPointX = firstPoint.getX();
		BigDecimal firstPointY = firstPoint.getY();
		BigDecimal secondPointX = secondPoint.getX();
		BigDecimal secondPointY = secondPoint.getY();

		BigDecimal numerator = firstPointX.subtract(secondPointX).multiply(testPoint.getY().subtract(secondPointY));
		BigDecimal divisor = firstPointY.subtract(secondPointY);

		return testPoint.getX()
				.compareTo(numerator.divide(divisor, MathConstants.DEFAULT_ROUNDING).add(secondPointX)) < 0;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void addPoint(Point point) {
		this.points.add(point);
	}

	@Override
	public String toString() {
		return "Polygon [points=" + points + "]";
	}

}
