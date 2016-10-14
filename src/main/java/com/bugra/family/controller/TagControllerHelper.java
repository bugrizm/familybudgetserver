package com.bugra.family.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.bugra.family.entity.Tag;
import com.bugra.family.entity.TagDTO;

@Service
public class TagControllerHelper {

	@PersistenceContext
	private EntityManager entityManager;
	
	public boolean hasTagWithName(String tagName) {
		boolean hasData = entityManager.createQuery("select t from Tag t where t.name = :tagName")
				.setParameter("tagName", tagName)
				.getResultList().size() > 0;
	
		if(hasData) {
			return true;
		}
		
		return false;
	}
	
	public void createTag(TagDTO tagDTO) {
		Tag newTag = new Tag();
		
		newTag.setName(tagDTO.getName());
		newTag.setIconText(tagDTO.getIconText());
		newTag.setColor(tagDTO.getColor());
		newTag.setId(tagDTO.getId());
		
		if(tagDTO.getId() != null) {
			entityManager.merge(newTag);
		} else {
			entityManager.persist(newTag);
		}
	}
	
}
