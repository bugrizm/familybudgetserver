package com.bugra.family.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bugra.family.businessrule.Result;
import com.bugra.family.controller.payment.DeletePaymentRule;
import com.bugra.family.controller.payment.SavePaymentRule;
import com.bugra.family.entity.Payment;

@RestController
@SuppressWarnings("unchecked")
public class PaymentController {

	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping(value = "/payment/{year}/{month}", method = RequestMethod.GET)
	public List<Payment> getPayments(@PathVariable("year") Short year, @PathVariable("month") Short month) {
		logger.info("getPayments with year and month");
		
		return entityManager.createQuery("SELECT p FROM Payment p WHERE year=:year AND month=:month")
				.setParameter("month", month)
				.setParameter("year", year)
				.getResultList();
	}
	
	@RequestMapping(value = "/payment/{year}/{month}/{tagName}", method = RequestMethod.GET)
	public List<Payment> getPayments(@PathVariable("year") Short year, @PathVariable("month") Short month, @PathVariable("tagName") String tagName) {
		logger.info("getPayments with year, month and tag");
		
		return entityManager.createQuery("SELECT p FROM Payment p, PaymentTag pt, Tag t WHERE p.id=pt.payment.id AND t.id=pt.tag.id AND p.year=:year AND p.month=:month AND t.name=:tagName")
				.setParameter("month", month)
				.setParameter("year", year)
				.setParameter("tagName", tagName)
				.getResultList();
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public Result savePayment(@RequestBody Payment payment) {
		logger.info("savePayment");
		
		return new SavePaymentRule(payment).apply();
	}
	
	@RequestMapping(value = "/payment/{paymentId}", method = RequestMethod.DELETE)
	public Result deletePayment(@PathVariable("paymentId") Integer paymentId) {
		logger.info("deletePayment");

		return deletePayment(paymentId, false);
	}
	
	@RequestMapping(value = "/payment/{paymentId}/{removeOtherInstallments}", method = RequestMethod.DELETE)
	public Result deletePayment(@PathVariable("paymentId") Integer paymentId, @PathVariable("removeOtherInstallments") boolean removeOtherInstallments) {
		logger.info("deletePayment");
		Payment removedPayment = entityManager.find(Payment.class, paymentId);
		
		return new DeletePaymentRule(removedPayment).apply();
	}
	
}
