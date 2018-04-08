package com.plivo.smsValidator.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;

public class SmsRequest {
	@HeaderParam("LoggedinAccountId")
	private Long loggedinAccountId;

	@FormParam("from")
	@NotNull
	@Size(min = 6, max = 16)
	private String from;

	@FormParam("to")
	@NotNull
	@Size(min = 6, max = 16)
	private String to;

	@FormParam("text")
	@NotNull
	@Size(min = 1, max = 120)
	private String text;

	@Override
	public String toString() {
		return "{ loggedinAccountId = " + loggedinAccountId + ", from = " + from + ", to = " + to + ", text = " + text + " }";
	}

	public Long getLoggedinAccountId() {
		return loggedinAccountId;
	}

	public void setLoggedinAccountId(Long loggedinAccountId) {
		this.loggedinAccountId = loggedinAccountId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}