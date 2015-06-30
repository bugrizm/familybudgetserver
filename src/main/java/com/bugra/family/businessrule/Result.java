package com.bugra.family.businessrule;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private boolean hasError;
	
	public Result() {
	}
	
	public Result(boolean hasError) {
		this.hasError = hasError;
	}
	
	public Result(String message, boolean hasError) {
		this.message = message;
		this.hasError = hasError;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean hasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	
}
