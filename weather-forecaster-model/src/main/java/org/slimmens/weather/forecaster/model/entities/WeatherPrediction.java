package org.slimmens.weather.forecaster.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slimmens.weather.forecaster.core.WeatherCondition;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "WEATHER_PREDICTIONS")
public class WeatherPrediction implements Serializable {

	private static final long serialVersionUID = -2012339837875354617L;

	@Id
	@Column(name = "DAY", nullable = false, unique = true)
	@JsonProperty(value = "día")
	private long day;

	@Enumerated(EnumType.STRING)
	@Column(name = "CONDITION", nullable = false)
	@JsonProperty(value = "clima")
	private WeatherCondition condition;

	@CreatedDate
	@Column(name = "CREATED_AT", nullable = false)
	@JsonProperty(value = "fechaDeCreación")
	private Date createdAt;

	@LastModifiedDate
	@Column(name = "UPDATED_AT", nullable = false)
	@JsonProperty(value = "fechaDeModificación")
	private Date updatedAt;

	public long getDay() {
		return day;
	}

	public void setDay(long day) {
		this.day = day;
	}

	public WeatherCondition getCondition() {
		return condition;
	}

	public void setCondition(WeatherCondition condition) {
		this.condition = condition;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "WeatherPrediction [day=" + day + ", condition=" + condition + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
