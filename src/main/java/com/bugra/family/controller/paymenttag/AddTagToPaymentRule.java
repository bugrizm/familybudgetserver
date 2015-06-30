package com.bugra.family.controller.paymenttag;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.paymenttag.action.AddTagToPaymentAction;
import com.bugra.family.controller.paymenttag.checker.IsTagNotAlreadyAdded;

public class AddTagToPaymentRule extends AbstractRule {

	public AddTagToPaymentRule(Integer paymentId, Integer tagId) {
		addChecker(new IsTagNotAlreadyAdded(paymentId, tagId));
		
		setAction(new AddTagToPaymentAction(paymentId, tagId));
	}
	
}
