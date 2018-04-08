package com.plivo.smsValidator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@RequestScoped
public class BeanValidationHandler {
	@Inject
	private Validator validator;

	public <T> List<String> validate(T bean) {
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		return convert(violations);
	}

	private <T> List<String> convert(Set<ConstraintViolation<T>> violations) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : violations) {
			errors.add(violation.getPropertyPath().toString() + " " + violation.getMessage());
		}
		return errors;
	}
}