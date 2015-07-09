package com.bugra.family.businessrule;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private boolean hasError;
	private int id;
	
	public Result() {
	}
	
	public Result(boolean hasError) {
		this.hasError = hasError;
	}
	
	public Result(String message, boolean hasError) {
		this.message = message;
		this.hasError = hasError;
	}
	
	public Result(String message, boolean hasError, int id) {
		this.message = message;
		this.hasError = hasError;
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean getHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
