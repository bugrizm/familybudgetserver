package com.bugra.family.controller.payment;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.payment.action.SavePaymentAction;
import com.bugra.family.controller.payment.checker.IsParametersNotNull;
import com.bugra.family.entity.Payment;

public class SavePaymentRule extends AbstractRule {
	
	public SavePaymentRule(Payment payment) {
		addChecker(new IsParametersNotNull(payment.getAmount(), payment.getDate(), payment.getMonth(), payment.getName(), payment.getYear()));
		setAction(new SavePaymentAction(payment));
	}
	
}
