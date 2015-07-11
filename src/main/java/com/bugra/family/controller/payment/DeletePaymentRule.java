package com.bugra.family.controller.payment;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.payment.action.DeletePaymentAction;
import com.bugra.family.entity.Payment;

public class DeletePaymentRule extends AbstractRule {
	
	public DeletePaymentRule(Payment payment, EntityManager entityManager) {
		setAction(new DeletePaymentAction(payment, entityManager));
	}
	
}
