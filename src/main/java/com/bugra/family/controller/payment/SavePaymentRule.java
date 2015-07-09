package com.bugra.family.controller.payment;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.payment.action.SavePaymentAction;
import com.bugra.family.controller.payment.checker.IsMultiplePaymentsNotHaveZeroInstallment;
import com.bugra.family.controller.payment.checker.IsParametersNotNull;
import com.bugra.family.entity.PaymentDTO;

public class SavePaymentRule extends AbstractRule {
	
	public SavePaymentRule(PaymentDTO payment, EntityManager entityManager) {
		addChecker(new IsParametersNotNull(payment.getAmount(), payment.getDate(), payment.getMonth(), payment.getName(), payment.getYear()));
		addChecker(new IsMultiplePaymentsNotHaveZeroInstallment(payment));
		setAction(new SavePaymentAction(payment, entityManager));
	}
	
}
