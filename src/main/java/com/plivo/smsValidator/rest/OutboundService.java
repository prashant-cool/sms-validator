package com.plivo.smsValidator.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.plivo.smsValidator.dao.PhoneNumberDAO;
import com.plivo.smsValidator.model.SmsRequest;
import com.plivo.smsValidator.model.SmsResponse;
import com.plivo.smsValidator.service.BeanValidationHandler;
import com.plivo.smsValidator.service.CacheService;

@Path("outbound/sms")
@RequestScoped
public class OutboundService {
	@Inject
	private Logger log;

	@Inject
	private BeanValidationHandler beanValidationHandler;

	@Inject
	private CacheService cacheService;

	@Inject
	private PhoneNumberDAO dao;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public SmsResponse validate(@BeanParam SmsRequest smsRequest) {
		log.info("smsRequest = " + smsRequest);
		List<String> errors = beanValidationHandler.validate(smsRequest);
		if (!errors.isEmpty()) {
			return new SmsResponse("", errors);
		} else if (cacheService.isPresentInStopCache(smsRequest.getTo(), smsRequest.getFrom())) {
			String error = "sms from " + smsRequest.getFrom() + " to " + smsRequest.getTo() + " blocked by STOP request";
			return new SmsResponse("", Arrays.asList(error));
		} else if (dao.findByPhoneNumber(smsRequest.getFrom(), smsRequest.getLoggedinAccountId()) == null) {
			return new SmsResponse("", Arrays.asList("from parameter not found"));
		} else if (cacheService.isWithinThreshold(smsRequest.getFrom())) {
			String error = "limit reached for from " + smsRequest.getFrom();
			return new SmsResponse("", Arrays.asList(error));
		}

		return new SmsResponse("outbound sms ok", new ArrayList<String>());
	}
}