package com.bugra.family.controller.payment.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Payment;
import com.bugra.family.entity.PaymentDTO;
import com.bugra.family.entity.PaymentTag;
import com.bugra.family.entity.Tag;

@Component
public class SavePaymentAction implements Action {

	private EntityManager entityManager;
	
	private PaymentDTO payment;
	
	public SavePaymentAction() {}
	
	public SavePaymentAction(PaymentDTO payment, EntityManager entityManager) {
		this.payment = payment;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result execute() {
		try {
			if(payment.getIsMultiple()) {
				createAndPersistMultiplePayments();
			} else {
				createAndPersistSinglePayment();
			}
			
		} catch(Exception e) {
			return new Result("HATA: " + e.getMessage(), true);
		}
		
		return new Result("Odeme basariyla eklendi.", false);
	}

	@SuppressWarnings("unchecked")
	private void createAndPersistSinglePayment() {
		Payment newPayment = new Payment();
		newPayment.setAmount(payment.getAmount());
		newPayment.setDate(getDateFromMillis());
		newPayment.setMonth(payment.getMonth());
		newPayment.setName(payment.getName());
		newPayment.setYear(payment.getYear());
		
		entityManager.persist(newPayment);
		
		List<Tag> tagList = entityManager.createQuery("select t from Tag t where id in (:tagIds)")
												.setParameter("tagIds", parseIdList())
												.getResultList();
		
		
		newPayment.setTags(new ArrayList<PaymentTag>());
		for (Tag tag : tagList) {
			PaymentTag newPt = new PaymentTag();
			newPt.setPayment(newPayment);
			newPt.setTag(tag);
			
			entityManager.persist(newPt);
		}
		
		
	}

	@SuppressWarnings("unchecked")
	private void createAndPersistMultiplePayments() {
		Payment parentPayment = new Payment();
		parentPayment.setDate(getDateFromMillis());
		parentPayment.setName(payment.getName());
		
		entityManager.persist(parentPayment);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, payment.getMonth());
		calendar.set(Calendar.YEAR, payment.getYear());
		
		for(int i=0; i<payment.getInstallmentAmount(); i++) {
			Payment newPayment = new Payment();
			newPayment.setAmount(payment.getAmount());
			newPayment.setDate(getDateFromMillis());
			
			newPayment.setMonth((short)calendar.get(Calendar.MONTH));
			newPayment.setName(payment.getName() + "(" + (i+1) + "/" + payment.getInstallmentAmount() + ")");
			newPayment.setYear((short)calendar.get(Calendar.YEAR));
			newPayment.setParentPayment(parentPayment);
			
			entityManager.persist(newPayment);
			
			calendar.add(Calendar.MONTH, 1);
			
			List<Tag> tagList = entityManager.createQuery("select t from Tag t where id in (:tagIds)")
					.setParameter("tagIds", parseIdList())
					.getResultList();


			newPayment.setTags(new ArrayList<PaymentTag>());
			
			for (Tag tag : tagList) {
				PaymentTag newPt = new PaymentTag();
				newPt.setPayment(newPayment);
				newPt.setTag(tag);
				
				entityManager.persist(newPt);
			}
		}
		
	}
	
	private List<Integer> parseIdList() {
		List<Integer> tagIdList = new ArrayList<Integer>();
		
		if(payment.getTagIdList() != null) {
			String[] tags = payment.getTagIdList().split(",");
			
			for (String string : tags) {
				if(!string.equals("")) {
					tagIdList.add(Integer.parseInt(string));
				}
			}
		}
		
		return tagIdList;
	}
	
	private Date getDateFromMillis() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(payment.getDate());
		return calendar.getTime();
	}

}
