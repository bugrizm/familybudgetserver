package com.bugra.family.controller.payment.checker;

import com.bugra.family.businessrule.Checker;
import com.bugra.family.businessrule.Result;

public class IsParametersNotNull implements Checker {

	private Object[] parameter;

	public IsParametersNotNull(Object ... parameter) {
		this.parameter = parameter;
	}
	
	@Override
	public Result check() {
		for (Object object : parameter) {
			if(object == null) {
				return new Result("Eksik veri giriþi.", true);
			}
		}
		return new Result(false);
	}

}
