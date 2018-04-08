package com.plivo.smsValidator.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.plivo.smsValidator.model.PhoneNumber;

@ApplicationScoped
public class PhoneNumberDAO {
	@Inject
	private EntityManager em;

	public PhoneNumber findByPhoneNumber(String phoneNumber, Long accountId) {
		String queryStr = "select p from PhoneNumber p where number = ? and accountId = ?";
		TypedQuery<PhoneNumber> query = em.createQuery(queryStr, PhoneNumber.class).setParameter(1, phoneNumber).setParameter(2, accountId);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}