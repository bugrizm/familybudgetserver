package com.bugra.family.controller.payment.action;

import java.math.BigDecimal;
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
	
	private PaymentDTO paymentDTO;
	
	public SavePaymentAction() {}
	
	public SavePaymentAction(PaymentDTO paymentDTO, EntityManager entityManager) {
		this.paymentDTO = paymentDTO;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result execute() {
		try {
			if(paymentDTO.getIsMultiple()) {
				createAndPersistMultiplePayments();
			} else {
				createAndPersistSinglePayment();
			}
			
		} catch(Exception e) {
			return new Result("HATA: " + e.getMessage(), true);
		}
		
		return new Result("Odeme basariyla eklendi.", false);
	}

	private void createAndPersistSinglePayment() {
		persistNewPayment(paymentDTO.getAmount(), getDateFromMillis(), paymentDTO.getMonth(), paymentDTO.getYear(), paymentDTO.getName(), null);
	}

	
	private void createAndPersistMultiplePayments() {
		Payment parentPayment = new Payment();
		parentPayment.setDate(getDateFromMillis());
		parentPayment.setName(paymentDTO.getName());
		
		entityManager.persist(parentPayment);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, paymentDTO.getMonth());
		calendar.set(Calendar.YEAR, paymentDTO.getYear());
		
		BigDecimal amount = paymentDTO.getAmount().divide(new BigDecimal(paymentDTO.getInstallmentAmount()));
		
		for(int i=0; i<paymentDTO.getInstallmentAmount(); i++) {
			short month = (short)calendar.get(Calendar.MONTH);
			short year = (short)calendar.get(Calendar.YEAR);
			String name = paymentDTO.getName() + "(" + (i+1) + "/" + paymentDTO.getInstallmentAmount() + ")";
			persistNewPayment(amount, getDateFromMillis(), month, year, name, parentPayment);
			
			calendar.add(Calendar.MONTH, 1);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void persistNewPayment(BigDecimal amount, Date date, short month, short year, String name, Payment parentPayment) {
		Payment newPayment = new Payment();
		newPayment.setId(newPayment.getId());
		newPayment.setAmount(amount);
		newPayment.setDate(date);
		
		newPayment.setMonth(month);
		newPayment.setName(name);
		newPayment.setYear(year);
		newPayment.setParentPayment(parentPayment);
		
		entityManager.persist(newPayment);
		
		List<Integer> tagIdList = parseIdList();
		
		if(!tagIdList.isEmpty()) {
			
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
		
		if(paymentDTO.getTagIdList() != null) {
			String[] tags = paymentDTO.getTagIdList().split(",");
			
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
		calendar.setTimeInMillis(paymentDTO.getDate());
		return calendar.getTime();
	}

}
