package com.bugra.family.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bugra.family.businessrule.Result;
import com.bugra.family.controller.paymenttag.AddTagToPaymentRule;

@RestController
public class PaymentTagController {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(PaymentTagController.class);

	@RequestMapping(value = "/paymenttag/{paymentId}/{tagId}", method = RequestMethod.POST)
	public Result addLabelToPayment(@PathVariable("paymentId") Integer paymentId, @PathVariable("tagId") Integer tagId) {
		logger.info("addLabelToPayment");
		
		return new AddTagToPaymentRule(paymentId, tagId, entityManager).apply();
	}
	
	@RequestMapping(value = "/paymenttag/{paymentId}/{tagId}", method = RequestMethod.DELETE)
	public Result removeLabelFromPayment(@PathVariable("paymentId") Integer paymentId, @PathVariable("tagId") Integer tagId) {
		logger.info("removeLabelFromPayment");
		
		entityManager.createQuery("delete from PaymentTag where paymentId=:paymentId and tagId=:tagId")
			.setParameter("paymentId", paymentId)
			.setParameter("tagId", tagId)
			.executeUpdate();
		
		return new Result("Etiket odemeden basariyla cikarilmistir.", false);
	}
	
}
