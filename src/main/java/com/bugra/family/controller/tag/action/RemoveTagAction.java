package com.bugra.family.controller.tag.action;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Tag;

public class RemoveTagAction implements Action {

	private EntityManager entityManager;
	private Tag tag;
	
	public RemoveTagAction(Tag tag, EntityManager entityManager) {
		this.tag = tag;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result execute() {
		entityManager.remove(tag);
		return new Result(false);
	}

}
