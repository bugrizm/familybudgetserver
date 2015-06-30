package com.bugra.family.controller.paymenttag.checker;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bugra.family.businessrule.Checker;
import com.bugra.family.businessrule.Result;

public class IsTagNotAlreadyAdded implements Checker {

	@PersistenceContext
	private EntityManager entityManager;
	
	private Integer paymentId;
	private Integer tagId;

	public IsTagNotAlreadyAdded(Integer paymentId, Integer tagId) {
		this.paymentId = paymentId;
		this.tagId = tagId;
	}
	
	@Override
	public Result check() {
		boolean hasData = entityManager.createQuery("select from PaymentTag where paymentId=:paymentId and tagId=:tagId")
							.setParameter("paymentId", paymentId)
							.setParameter("tagId", tagId)
							.getResultList().size() > 0;
		
		if(hasData) {
			return new Result("Etiket ödemeye zaten eklenmiş", true);
		}
		
		return new Result(false);
	}

}
