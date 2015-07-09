package com.bugra.family.controller.paymenttag.checker;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.Checker;
import com.bugra.family.businessrule.Result;

public class IsTagNotAlreadyAdded implements Checker {

	private EntityManager entityManager;
	
	private Integer paymentId;
	private Integer tagId;

	public IsTagNotAlreadyAdded(Integer paymentId, Integer tagId, EntityManager entityManager) {
		this.paymentId = paymentId;
		this.tagId = tagId;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result check() {
		boolean hasData = entityManager.createQuery("select pt from PaymentTag pt where payment.id=:paymentId and tag.id=:tagId")
							.setParameter("paymentId", paymentId)
							.setParameter("tagId", tagId)
							.getResultList().size() > 0;
		
		if(hasData) {
			return new Result("Etiket odemeye zaten eklenmis", true);
		}
		
		return new Result(false);
	}

}
