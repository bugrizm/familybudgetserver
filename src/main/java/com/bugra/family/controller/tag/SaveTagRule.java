package com.bugra.family.controller.tag;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.tag.action.SaveTagAction;
import com.bugra.family.controller.tag.checker.IsTagWithSameNameNotExists;
import com.bugra.family.entity.TagDTO;

public class SaveTagRule extends AbstractRule {
	
	public SaveTagRule(TagDTO tagDTO, EntityManager entityManager) {
		addChecker(new IsTagWithSameNameNotExists(tagDTO.getName(), entityManager));
		
		setAction(new SaveTagAction(tagDTO, entityManager));
	}
	
}
