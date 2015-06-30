package com.bugra.family.controller.tag;

import com.bugra.family.businessrule.AbstractRule;
import com.bugra.family.controller.tag.action.RemoveTagAction;
import com.bugra.family.entity.Tag;

public class RemoveTagRule extends AbstractRule {
	
	public RemoveTagRule(Tag tag) {
		setAction(new RemoveTagAction(tag));
	}
	
}
