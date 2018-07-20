package org.slimmens.weather.forecaster.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7108019588619856723L;

	public NotFoundException(String message) {
		super(message);
	}

}
