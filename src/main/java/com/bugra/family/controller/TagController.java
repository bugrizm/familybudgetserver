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
import com.bugra.family.controller.tag.RemoveTagRule;
import com.bugra.family.controller.tag.SaveTagRule;
import com.bugra.family.entity.Tag;

@RestController
@SuppressWarnings("unchecked")
public class TagController {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public List<Tag> getTags() {
		logger.info("getTags");
		
		return entityManager.createQuery("select t from Tag t").getResultList();
	}
	
	@RequestMapping(value = "/tag", method = RequestMethod.POST)
	public Result saveTag(@RequestBody Tag tag) {
		logger.info("saveTag");
		
		return new SaveTagRule(tag, entityManager).apply();
	}
	
	@RequestMapping(value = "/tag/{tagId}", method = RequestMethod.DELETE)
	public Result deleteTag(@PathVariable("tagId") Integer tagId) {
		logger.info("deleteTag");

		Tag removedTag = entityManager.find(Tag.class, tagId);
		
		return new RemoveTagRule(removedTag, entityManager).apply();
	}

}
