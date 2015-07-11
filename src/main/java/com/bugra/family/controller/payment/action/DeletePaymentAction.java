package com.bugra.family.controller.payment.action;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Payment;

public class DeletePaymentAction implements Action {

	private EntityManager entityManager;
	
	private Payment payment;
	
	public DeletePaymentAction(Payment payment, EntityManager entityManager) {
		this.payment = payment;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result execute() {
		try {
			if(payment.getParentPayment() != null) {
				Payment parentPayment = entityManager.find(Payment.class, payment.getParentPayment().getId());
				entityManager.remove(parentPayment);
			} else {
				entityManager.remove(payment);
			}
		} catch(Exception e) {
			return new Result("HATA: " + e.getMessage(), true);
		}
		
		return new Result("Odeme basariyla silindi.", false);
		
	}

}
