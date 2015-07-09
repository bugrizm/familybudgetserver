package com.bugra.family.entity;

import java.math.BigDecimal;

public class MonthSummaryDTO {
	
	private Integer tagId;
	private BigDecimal totalAmount;
	
	public MonthSummaryDTO(Integer tagId, BigDecimal totalAmount) {
		this.tagId = tagId;
		this.totalAmount = totalAmount;
	}
	
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
