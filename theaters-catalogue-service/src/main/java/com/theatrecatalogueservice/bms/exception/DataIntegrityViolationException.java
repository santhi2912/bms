/**
 * 
 */
package com.booking.bms.exception;

import lombok.Getter;

/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
@Getter
public class DataIntegrityViolationException extends RuntimeException {

	private static final long serialVersionUID = 646182706219385282L;

	private final String message;

	public DataIntegrityViolationException(String message) {
		super(message);
		this.message = message;
	}

}