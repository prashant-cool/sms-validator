package com.plivo.smsValidator.model;

import java.util.List;

public class SmsResponse {
	private String message;
	private List<String> errors;

	public SmsResponse() {
	}

	public SmsResponse(String message, List<String> errors) {
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}