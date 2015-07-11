package com.bugra.family.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bugra.family.businessrule.Result;
import com.bugra.family.controller.payment.DeletePaymentRule;
import com.bugra.family.controller.payment.SavePaymentRule;
import com.bugra.family.entity.MonthSummaryDTO;
import com.bugra.family.entity.Payment;
import com.bugra.family.entity.PaymentDTO;

@RestController
@SuppressWarnings("unchecked")
@Transactional
public class PaymentController {

	@PersistenceContext
	private EntityManager entityManager;
		
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping(value = "/payment/{year}/{month}", method = RequestMethod.GET)
	public List<Payment> getPayments(@PathVariable("year") Short year, @PathVariable("month") Short month) {
		logger.info("getPayments with year and month");
		
		List<Payment> deneme = entityManager.createQuery("SELECT p FROM Payment p WHERE year=:year AND month=:month")
				.setParameter("month", month)
				.setParameter("year", year)
				.getResultList();
		
		return deneme;
	}
	
	@RequestMapping(value = "/payment/{year}/{month}/{tagName}", method = RequestMethod.GET)
	public List<Payment> getPayments(@PathVariable("year") Short year, @PathVariable("month") Short month, @PathVariable("tagName") Integer tagId) {
		logger.info("getPayments with year, month and tag");
		
		return entityManager.createQuery("SELECT p FROM Payment p, PaymentTag pt, Tag t WHERE p.id=pt.payment.id AND t.id=pt.tag.id AND p.year=:year AND p.month=:month AND t.id=:tagId")
				.setParameter("month", month)
				.setParameter("year", year)
				.setParameter("tagId", tagId)
				.getResultList();
	}
	
	@RequestMapping(value = "/month_summary/{year}/{month}", method = RequestMethod.GET)
	public List<MonthSummaryDTO> getMonthSummary(@PathVariable("year") Short year, @PathVariable("month") Short month) {
		logger.info("getMonthSummary");
		
		List<Object[]> resultList = entityManager.createNativeQuery("select pt.tag_id, sum(p.amount) from payment p inner join payment_tag pt on p.id = pt.payment_id "
																	+ "and p.month=:month and p.year = :year group by pt.tag_id")
												.setParameter("month", month)
												.setParameter("year", year)
												.getResultList();
		
		BigDecimal totalAmount = (BigDecimal) entityManager.createNativeQuery("select sum(amount) from payment where month=:month and year = :year")
															.setParameter("month", month)
															.setParameter("year", year)
															.getSingleResult();
		
		List<MonthSummaryDTO> monthSummaryList = new ArrayList<MonthSummaryDTO>();
		monthSummaryList.add(new MonthSummaryDTO(null, totalAmount));
		
		for (Object[] objects : resultList) {
			monthSummaryList.add(new MonthSummaryDTO((Integer)objects[0], (BigDecimal)objects[1]));
		}
		
		return monthSummaryList;
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public Result savePayment(@RequestBody PaymentDTO payment) {
		logger.info("savePayment");
		
		return new SavePaymentRule(payment, entityManager).apply();
	}
	
	@RequestMapping(value = "/payment/{paymentId}", method = RequestMethod.DELETE)
	public Result deletePayment(@PathVariable("paymentId") Integer paymentId) {
		logger.info("deletePayment");

		Payment removedPayment = entityManager.find(Payment.class, paymentId);
		
		return new DeletePaymentRule(removedPayment, entityManager).apply();
	}
	
}
