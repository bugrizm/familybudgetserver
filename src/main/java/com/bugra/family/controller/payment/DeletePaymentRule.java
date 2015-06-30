package com.bugra.family.controller.payment;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.payment.action.DeletePaymentAction;
import com.bugra.family.entity.Payment;

public class DeletePaymentRule extends AbstractRule {
	
	public DeletePaymentRule(Payment payment) {
		setAction(new DeletePaymentAction(payment));
	}
	
}
