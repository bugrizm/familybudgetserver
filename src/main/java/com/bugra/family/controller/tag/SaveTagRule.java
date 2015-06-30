package com.bugra.family.controller.tag;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.tag.action.SaveTagAction;
import com.bugra.family.controller.tag.checker.IsTagWithSameNameNotExists;
import com.bugra.family.entity.Tag;

public class SaveTagRule extends AbstractRule {
	
	public SaveTagRule(Tag tag) {
		addChecker(new IsTagWithSameNameNotExists(tag.getName()));
		
		setAction(new SaveTagAction(tag));
	}
	
}
