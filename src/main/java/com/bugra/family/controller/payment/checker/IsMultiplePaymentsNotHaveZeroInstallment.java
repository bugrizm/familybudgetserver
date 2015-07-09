package com.bugra.family.controller.payment.checker;

import com.bugra.family.businessrule.Checker;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.PaymentDTO;

public class IsMultiplePaymentsNotHaveZeroInstallment implements Checker {

	private PaymentDTO payment;

	public IsMultiplePaymentsNotHaveZeroInstallment(PaymentDTO payment) {
		this.payment = payment;
	}
	
	@Override
	public Result check() {
		if(payment.getIsMultiple() && payment.getInstallmentAmount() <= 0) {
			return new Result("Taksit sayisi 0'dan buyuk bir deger olmalidir.", true);
		}
		
		return new Result(false);
	}

}
