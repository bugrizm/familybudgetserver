package com.bugra.family.controller;

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

import com.bugra.family.entity.Tag;
import com.bugra.family.entity.TagDTO;

@RestController
@SuppressWarnings("unchecked")
@Transactional
public class TagController {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private TagControllerHelper helper;

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public List<Tag> getTags() {
		logger.info("getTags");
		
		return entityManager.createQuery("SELECT t FROM Tag t ORDER BY t.name").getResultList();
	}
	
	@RequestMapping(value = "/tag", method = RequestMethod.POST)
	public void saveTag(@RequestBody TagDTO tagDTO) {
		logger.info("saveTag");
		
		if(helper.hasTagWithName(tagDTO.getName())){
			throw new RuntimeException("Aynı isimde bir etiket bulunmaktadır.");
		}
		
		helper.createTag(tagDTO);
	}
	
	@RequestMapping(value = "/tag/{tagId}", method = RequestMethod.DELETE)
	public void deleteTag(@PathVariable("tagId") Integer tagId) {
		logger.info("deleteTag");

		Tag removedTag = entityManager.find(Tag.class, tagId);
		
		entityManager.remove(removedTag);
	}

}
