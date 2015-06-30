package com.bugra.family.controller.tag.action;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Tag;

public class SaveTagAction implements Action {

	@PersistenceContext
	private EntityManager entityManager;
	private Tag tag;
	
	public SaveTagAction(Tag tag) {
		this.tag = tag;
	}
	
	@Override
	public Result execute() {
		entityManager.persist(tag);
		return new Result(false);
	}

}