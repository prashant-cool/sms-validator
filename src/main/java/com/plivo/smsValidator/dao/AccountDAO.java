package com.plivo.smsValidator.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.plivo.smsValidator.model.Account;

@ApplicationScoped
public class AccountDAO {
	@Inject
	private EntityManager em;

	public Account findByUsername(String username) {
		TypedQuery<Account> query = em.createQuery("select a from Account a where username = ?", Account.class).setParameter(1, username);
		try {
			return query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
}