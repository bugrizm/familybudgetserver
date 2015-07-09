package com.bugra.family.controller.paymenttag.action;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Payment;
import com.bugra.family.entity.PaymentTag;
import com.bugra.family.entity.Tag;

public class AddTagToPaymentAction implements Action {
	
	private EntityManager entityManager;
	
	private Integer paymentId;
	private Integer tagId;

	public AddTagToPaymentAction(Integer paymentId, Integer tagId, EntityManager entityManager) {
		this.paymentId = paymentId;
		this.tagId = tagId;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result execute() {
		PaymentTag paymentTag = new PaymentTag();
		
		Payment attachedPayment = entityManager.find(Payment.class, paymentId);
		Tag attachedTag = entityManager.find(Tag.class, tagId);
		
		if(attachedPayment == null || attachedTag == null) {
			return new Result("Ilgili odeme veya etiket veritabaninda bulunmamaktadir.", true);
		}
		
		paymentTag.setPayment(attachedPayment);
		paymentTag.setTag(attachedTag);
		
		entityManager.persist(paymentTag);
		
		return new Result("Etiket odemeye basariyla eklendi.", false);
	}

}
