package com.bugra.family.entity;

import java.math.BigDecimal;


public class TagDTO {
	
	private Integer id;
	private String name;
	private String iconText;
	private String color;
	private BigDecimal limit;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIconText() {
		return iconText;
	}
	public void setIconText(String iconText) {
		this.iconText = iconText;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public BigDecimal getLimit() {
		return limit;
	}
	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
    
	
}
