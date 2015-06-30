package com.bugra.family.controller.payment.action;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Payment;

public class SavePaymentAction implements Action {

	@PersistenceContext
	private EntityManager entityManager;
	
	private Payment payment;
	
	public SavePaymentAction(Payment payment) {
		this.payment = payment;
	}
	
	@Override
	public Result execute() {
		try {
			entityManager.persist(payment);
		} catch(Exception e) {
			return new Result("HATA: " + e.getMessage(), true);
		}
		
		return new Result("Odeme basariyla eklendi.", false);
	}

}
