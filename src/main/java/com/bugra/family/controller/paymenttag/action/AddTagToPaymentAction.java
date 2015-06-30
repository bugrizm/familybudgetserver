package com.bugra.family.controller.paymenttag.action;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Payment;
import com.bugra.family.entity.PaymentTag;
import com.bugra.family.entity.Tag;

public class AddTagToPaymentAction implements Action {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Integer paymentId;
	private Integer tagId;

	public AddTagToPaymentAction(Integer paymentId, Integer tagId) {
		this.paymentId = paymentId;
		this.tagId = tagId;
	}
	
	@Override
	public Result execute() {
		PaymentTag paymentTag = new PaymentTag();
		
		Payment attachedPayment = entityManager.find(Payment.class, paymentId);
		Tag attachedTag = entityManager.find(Tag.class, tagId);
		
		if(attachedPayment == null || attachedTag == null) {
			return new Result("Ýlgili ödeme veya etiket veritabanýnda bulunmamaktadýr.", true);
		}
		
		paymentTag.setPayment(attachedPayment);
		paymentTag.setTag(attachedTag);
		
		entityManager.persist(paymentTag);
		
		return new Result("Etiket ödemeye baþarýyla eklendi.", false);
	}

}
