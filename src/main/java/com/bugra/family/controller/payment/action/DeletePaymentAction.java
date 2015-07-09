package com.bugra.family.controller.payment.action;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Payment;

public class DeletePaymentAction implements Action {

	private EntityManager entityManager;
	
	private Payment payment;

	private boolean removeOtherInstallments;
	
	public DeletePaymentAction(Payment payment, boolean removeOtherInstallments, EntityManager entityManager) {
		this.payment = payment;
		this.removeOtherInstallments = removeOtherInstallments;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result execute() {
		try {
			if(removeOtherInstallments && payment.getParentPayment() != null) {
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
