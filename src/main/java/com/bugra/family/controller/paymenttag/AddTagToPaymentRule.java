package com.bugra.family.controller.paymenttag;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.paymenttag.action.AddTagToPaymentAction;
import com.bugra.family.controller.paymenttag.checker.IsTagNotAlreadyAdded;

public class AddTagToPaymentRule extends AbstractRule {

	public AddTagToPaymentRule(Integer paymentId, Integer tagId, EntityManager entityManager) {
		addChecker(new IsTagNotAlreadyAdded(paymentId, tagId, entityManager));
		
		setAction(new AddTagToPaymentAction(paymentId, tagId, entityManager));
	}
	
}
