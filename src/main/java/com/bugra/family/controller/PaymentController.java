package com.bugra.family.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bugra.family.entity.Payment;
import com.bugra.family.entity.PaymentDTO;
import com.bugra.family.entity.SummaryDTO;

@RestController
@SuppressWarnings("unchecked")
@Transactional
public class PaymentController {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaymentControllerHelper helper;
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping(value = "/payment/{year}/{month}", method = RequestMethod.GET)
	public List<Payment> getPayments(@PathVariable("year") Short year, @PathVariable("month") Short month) {
		logger.info("getPayments with year and month");
				
		return entityManager.createQuery("SELECT p FROM Payment p WHERE year=:year AND month=:month")
				.setParameter("month", month)
				.setParameter("year", year)
				.getResultList();
	}
	
	@RequestMapping(value = "/payment/{year}/{month}/{tagId}", method = RequestMethod.GET)
	public List<Payment> getPayments(@PathVariable("year") Short year, @PathVariable("month") Short month, @PathVariable("tagId") Integer tagId) {
		logger.info("getPayments with year, month and tag");
		
		return entityManager.createQuery("SELECT p FROM Payment p WHERE year=:year AND month=:month AND tag.id=:tagId")
				.setParameter("month", month)
				.setParameter("year", year)
				.setParameter("tagId", tagId)
				.getResultList();
	}
	
	@RequestMapping(value = "/month_summary/{year}/{month}", method = RequestMethod.GET)
	public List<SummaryDTO> getMonthSummary(@PathVariable("year") Short year, @PathVariable("month") Short month) {
		logger.info("getMonthSummary");
		
		List<Object[]> resultList = entityManager.createNativeQuery("select p.tag_id, sum(p.amount) from payment p inner join tag t "
																	+ "where p.tag_id = t.id and p.month=:month and p.year = :year group by p.tag_id order by t.name")
												.setParameter("month", month)
												.setParameter("year", year)
												.getResultList();
		
		BigDecimal totalAmount = (BigDecimal) entityManager.createNativeQuery("select sum(amount) from payment where month=:month and year = :year")
															.setParameter("month", month)
															.setParameter("year", year)
															.getSingleResult();
		
		List<SummaryDTO> monthSummaryList = new ArrayList<SummaryDTO>();
		monthSummaryList.add(new SummaryDTO(null, totalAmount));
		
		for (Object[] objects : resultList) {
			monthSummaryList.add(new SummaryDTO((Integer)objects[0], (BigDecimal)objects[1]));
		}
		
		return monthSummaryList;
	}
	
	@RequestMapping(value = "/year_summary/{year}", method = RequestMethod.GET)
	public List<SummaryDTO> getYearSummary(@PathVariable("year") Short year) {
		logger.info("getYearSummary");
		
		List<Object[]> resultList = entityManager.createNativeQuery("select p.tag_id, sum(p.amount) from payment p inner join tag t "
																	+ "where p.tag_id = t.id and p.year = :year group by p.tag_id order by t.name")
												.setParameter("year", year)
												.getResultList();
				
		BigDecimal totalAmount = (BigDecimal) entityManager.createNativeQuery("select sum(amount) from payment where year = :year")
															.setParameter("year", year)
															.getSingleResult();
		
		List<SummaryDTO> yearSummaryList = new ArrayList<SummaryDTO>();
		yearSummaryList.add(new SummaryDTO(null, totalAmount));
		
		for (Object[] objects : resultList) {
			yearSummaryList.add(new SummaryDTO((Integer)objects[0], (BigDecimal)objects[1]));
		}
		
		return yearSummaryList;
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public void savePayment(@RequestBody PaymentDTO payment) {
		logger.info("savePayment");
		
		if(!helper.isParametersForSaveOperationEnough(payment)) {
			throw new RuntimeException("Eksik veri girişi.");
		}
		if(payment.getIsMultiple() && payment.getInstallmentAmount() <= 0) {
			throw new RuntimeException("Taksit sayisi 0'dan buyuk bir deger olmalidir.");
		}
		
		if(payment.getIsMultiple()) {
			helper.createMultiplePayments(payment);
		} else {
			helper.createSinglePayment(payment);
		}
	}
	
	@RequestMapping(value = "/payment/{paymentId}", method = RequestMethod.DELETE)
	public void deletePayment(@PathVariable("paymentId") Integer paymentId) {
		logger.info("deletePayment");

		Payment removedPayment = entityManager.find(Payment.class, paymentId);
		
		helper.deletePayment(removedPayment);
	}
	
	@RequestMapping(value = "/transfer_exceeded_budgets/{year}/{month}", method = RequestMethod.POST)
	public void transferExceededBudgetsToNextMonth(@PathVariable("year") Short year, @PathVariable("month") Short month) {
		helper.transferBudgets(year, month);
	}
	
}
