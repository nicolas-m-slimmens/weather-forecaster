package org.slimmens.weather.forecaster.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -1409152283315784869L;

	public BadRequestException(String message) {
		super(message);
	}

}
