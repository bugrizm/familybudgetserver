package com.bugra.family.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.bugra.family.entity.Payment;
import com.bugra.family.entity.PaymentDTO;
import com.bugra.family.entity.Tag;

@Service
@SuppressWarnings("unchecked")
public class PaymentControllerHelper {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void deletePayment(Payment payment) {
		if(payment.getParentPayment() != null) {
			Payment parentPayment = entityManager.find(Payment.class, payment.getParentPayment().getId());
			entityManager.remove(parentPayment);
		} else {
			entityManager.remove(payment);
		}
	}
	
	public boolean isParametersForSaveOperationEnough(PaymentDTO payment) {
		if(payment.getAmount()==null || payment.getDate()==null || payment.getMonth()==null || payment.getName()==null || payment.getYear()==null) {
			return false;
		}
		
		return true;
	}
	
	public void createSinglePayment(PaymentDTO paymentDTO) {
		persistNewPayment(paymentDTO.getAmount(), getDateFromMillis(paymentDTO), paymentDTO.getMonth(), paymentDTO.getYear(), paymentDTO.getName(), null, paymentDTO.getTagId());
	}
	
	public void createMultiplePayments(PaymentDTO paymentDTO) {
		Payment parentPayment = new Payment();
		parentPayment.setDate(getDateFromMillis(paymentDTO));
		parentPayment.setName(paymentDTO.getName());
		
		entityManager.persist(parentPayment);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, paymentDTO.getMonth());
		calendar.set(Calendar.MONTH, paymentDTO.getMonth());
		calendar.set(Calendar.YEAR, paymentDTO.getYear());
		
		BigDecimal amount = paymentDTO.getAmount().divide(new BigDecimal(paymentDTO.getInstallmentAmount()), 2, RoundingMode.HALF_DOWN);
		
		for(int i=0; i<paymentDTO.getInstallmentAmount(); i++) {
			short month = (short)calendar.get(Calendar.MONTH);
			short year = (short)calendar.get(Calendar.YEAR);
			String name = paymentDTO.getName() + "(" + (i+1) + "/" + paymentDTO.getInstallmentAmount() + ")";
			persistNewPayment(amount, getDateFromMillis(paymentDTO), month, year, name, parentPayment, paymentDTO.getTagId());
			
			calendar.add(Calendar.MONTH, 1);
		}
		
	}
	
	private void persistNewPayment(BigDecimal amount, Date date, short month, short year, String name, Payment parentPayment, int paymentTagId) {
		Payment newPayment = new Payment();
		newPayment.setId(newPayment.getId());
		newPayment.setAmount(amount);
		newPayment.setDate(date);
		
		newPayment.setMonth(month);
		newPayment.setName(name);
		newPayment.setYear(year);
		newPayment.setParentPayment(parentPayment);
		
		Tag tag = entityManager.find(Tag.class, paymentTagId);
		
		newPayment.setTag(tag);
		
		entityManager.persist(newPayment);
	}
	
	private Date getDateFromMillis(PaymentDTO paymentDTO) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(paymentDTO.getDate());
		return calendar.getTime();
	}
	
	public void transferBudgets(short year, short month) {
		List<Tag> tagList = entityManager.createQuery("select t from Tag t where limit is not null").getResultList();
		
		for (Tag tag : tagList) {
			BigDecimal totalSpentAmount = (BigDecimal) entityManager.createNativeQuery("select sum(p.amount) from payment p "
																		+ "where p.year = :year and p.month = :month and p.tag_id = :tagId")
														.setParameter("month", month)
														.setParameter("year", year)
														.setParameter("tagId", tag.getId())
														.getSingleResult();
			if(totalSpentAmount == null) {
				totalSpentAmount = BigDecimal.ZERO;
			}
			
			BigDecimal differenceAmount = totalSpentAmount.multiply(new BigDecimal(-1)).subtract(tag.getLimit());
			
			if(differenceAmount.equals(BigDecimal.ZERO)) {
				continue;
			}
			
			persistNewPayment(differenceAmount, Calendar.getInstance().getTime(), month, year, "Fazla harcama", null, tag.getId());
			
			differenceAmount = differenceAmount.multiply(new BigDecimal(-1));
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.YEAR, year);
			calendar.add(Calendar.MONTH, 1);
							
			persistNewPayment(differenceAmount, Calendar.getInstance().getTime(), (short)calendar.get(Calendar.MONTH), (short)calendar.get(Calendar.YEAR), "Onceki aydan transfer edilen", null, tag.getId());
		}
	}
}
