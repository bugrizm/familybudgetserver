package com.bugra.family.entity;

import java.math.BigDecimal;

public class PaymentDTO {
	
	private BigDecimal amount;
	private Long date;
	private Short month;
	private String name;
	private Short year;
	private int tagId;
    private boolean isMultiple;
    private int installmentAmount;
    
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public Short getMonth() {
		return month;
	}
	public void setMonth(Short month) {
		this.month = month;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getYear() {
		return year;
	}
	public void setYear(Short year) {
		this.year = year;
	}
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	public boolean getIsMultiple() {
		return isMultiple;
	}
	public void setIsMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}
	public int getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(int installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	
}
