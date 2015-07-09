package com.bugra.family.controller.tag.checker;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.Checker;
import com.bugra.family.businessrule.Result;

public class IsTagWithSameNameNotExists implements Checker {

	private EntityManager entityManager;
	
	private String tagName;

	public IsTagWithSameNameNotExists(String tagName, EntityManager entityManager) {
		this.tagName = tagName;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result check() {
		boolean hasData = entityManager.createQuery("select t from Tag t where t.name = :tagName")
										.setParameter("tagName", tagName)
										.getResultList().size() > 0;
		
		if(hasData) {
			return new Result("Ayni isimde bir etiket bulunmaktadir.", true);
		}
		
		return new Result(false);
	}

}
