package com.bugra.family.controller.payment.action;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Payment;

public class DeletePaymentAction implements Action {

	@PersistenceContext
	private EntityManager entityManager;
	
	private Payment payment;
	
	public DeletePaymentAction(Payment payment) {
		this.payment = payment;
	}
	
	@Override
	public Result execute() {
		try {
			entityManager.remove(payment);
		} catch(Exception e) {
			return new Result("HATA: " + e.getMessage(), true);
		}
		
		return new Result("Ödeme baþarýyla silindi.", false);
		
	}

}
