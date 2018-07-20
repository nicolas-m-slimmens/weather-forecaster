package org.slimmens.weather.forecaster.core.math;

import java.math.BigDecimal;

public class Angle implements Comparable<Angle> {

	public static final int MAX_DEGREES = 360;
	public static final int MAX_MINUTES = 60;
	public static final BigDecimal MAX_SECONDS = new BigDecimal(60);

	public static final int MIN_DEGREES = 0;
	public static final int MIN_MINUTES = 0;
	public static final BigDecimal MIN_SECONDS = BigDecimal.ZERO;

	private int degrees;

	private int minutes;

	private BigDecimal seconds;

	public Angle(int degrees, int minutes, BigDecimal seconds) {
		super();
		this.degrees = degrees;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public Angle(Angle angle) {
		this(angle.getDegrees(), angle.getMinutes(), angle.getSeconds());
	}

	public Angle(int degrees, int minutes) {
		this(degrees, minutes, MIN_SECONDS);
	}

	public Angle(int degrees) {
		this(degrees, MIN_MINUTES);
	}

	public Angle() {
		this(MIN_DEGREES);
	}

	public void increaseAngle(Angle angle) {
		this.increaseDegrees(angle.getDegrees());
		this.increaseMinutes(angle.getMinutes());
		this.increaseSeconds(angle.getSeconds());
	}

	public void increaseDegrees(int degrees) {
		this.setDegrees(this.degrees + degrees);
	}

	public void increaseMinutes(int minutes) {
		this.setMinutes(this.minutes + minutes);
	}

	public void increaseSeconds(BigDecimal seconds) {
		this.setSeconds(this.seconds.add(seconds));
	}

	public void reduceAngle(Angle angle) {
		this.reduceDegrees(angle.getDegrees());
		this.reduceMinutes(angle.getMinutes());
		this.reduceSeconds(angle.getSeconds());
	}

	public void reduceDegrees(int degrees) {
		this.setDegrees(this.degrees - degrees);
	}

	public void reduceMinutes(int minutes) {
		this.setMinutes(this.minutes - minutes);
	}

	public void reduceSeconds(BigDecimal seconds) {
		this.setSeconds(this.seconds.subtract(seconds));
	}

	public Angle add(Angle angle) {
		Angle newAngle = new Angle(this);

		newAngle.increaseAngle(angle);
		return newAngle;
	}

	public Angle substract(Angle angle) {
		Angle newAngle = new Angle(this);

		newAngle.reduceAngle(angle);
		return newAngle;
	}

	public int getDegrees() {
		return degrees;
	}

	public void setDegrees(int degrees) {
		this.degrees = degrees % MAX_DEGREES;

		if (this.degrees < MIN_DEGREES) {
			this.degrees += MAX_DEGREES;
		}
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		if (minutes < MIN_MINUTES) {
			this.minutes = minutes % MAX_MINUTES + MAX_MINUTES;
			this.increaseDegrees((minutes - MAX_MINUTES) / MAX_MINUTES);
		} else {
			this.minutes = minutes % MAX_MINUTES;
			this.increaseDegrees(minutes / MAX_MINUTES);
		}
	}

	public BigDecimal getSeconds() {
		return seconds;
	}

	public void setSeconds(BigDecimal seconds) {
		if (seconds.compareTo(MIN_SECONDS) < 0) {
			this.seconds = seconds.remainder(MAX_SECONDS).add(MAX_SECONDS);
			this.increaseMinutes(
					seconds.subtract(MAX_SECONDS).divide(MAX_SECONDS, MathConstants.DEFAULT_ROUNDING).intValue());
		} else {
			this.seconds = seconds.remainder(MAX_SECONDS);
			this.increaseMinutes(seconds.divide(MAX_SECONDS, MathConstants.DEFAULT_ROUNDING).intValue());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Angle other = (Angle) obj;
		if (this.degrees != other.degrees)
			return false;
		if (this.minutes != other.minutes)
			return false;
		if (!this.seconds.equals(other.seconds))
			return false;
		return true;
	}

	@Override
	public int compareTo(Angle angle) {
		int degreesComparison = Integer.compare(this.degrees, angle.getDegrees());
		if (degreesComparison != 0) {
			return degreesComparison;
		}

		int minutesComparison = Integer.compare(this.minutes, angle.getMinutes());
		if (minutesComparison != 0) {
			return minutesComparison;
		}

		return this.seconds.compareTo(angle.getSeconds());
	}

	public double toDouble() {
		double decimalValue = this.degrees;
		decimalValue += this.minutes / 60;
		decimalValue += this.seconds.doubleValue() / 60 * 60;

		return decimalValue;
	}

	@Override
	public String toString() {
		return degrees + "° " + minutes + "' " + seconds + "\"";
	}

}
