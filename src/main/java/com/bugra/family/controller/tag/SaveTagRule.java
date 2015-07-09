package com.bugra.family.controller.tag;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.tag.action.SaveTagAction;
import com.bugra.family.controller.tag.checker.IsTagWithSameNameNotExists;
import com.bugra.family.entity.Tag;

public class SaveTagRule extends AbstractRule {
	
	public SaveTagRule(Tag tag, EntityManager entityManager) {
		addChecker(new IsTagWithSameNameNotExists(tag.getName(), entityManager));
		
		setAction(new SaveTagAction(tag, entityManager));
	}
	
}
