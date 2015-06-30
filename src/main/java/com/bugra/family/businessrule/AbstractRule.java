package com.bugra.family.businessrule;

import java.util.ArrayList;
import java.util.List;

public class AbstractRule implements Rule {

	private List<Checker> checkerList;
	private Action action;
	
	@Override
	public Result apply() {
		for (Checker checker : checkerList) {
			Result result = checker.check();
			
			if(result.hasError()) {
				return result;
			}
		}
		
		return action.execute();
	}

	protected void addChecker(Checker checker) {
		if(checkerList == null) {
			checkerList = new ArrayList<Checker>();
		}
		
		checkerList.add(checker);
	}
	
	protected void setAction(Action action) {
		this.action = action;
	}
}
