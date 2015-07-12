package com.bugra.family.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the tag database table.
 * 
 */
@Entity
@Table(name="tag")
@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	@OneToMany(mappedBy="tag")
	@JsonIgnore
	private List<PaymentTag> payments;
	
	private String color;
	
	@Column(name="icon_text")
	private String iconText;
	
	public Tag() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PaymentTag> getPayments() {
		return this.payments;
	}

	public void setPayments(List<PaymentTag> payments) {
		this.payments = payments;
	}

	public PaymentTag addPayment(PaymentTag payment) {
		getPayments().add(payment);
		payment.setTag(this);

		return payment;
	}

	public PaymentTag removePayment(PaymentTag payment) {
		getPayments().remove(payment);
		payment.setTag(null);

		return payment;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIconText() {
		return iconText;
	}

	public void setIconText(String iconText) {
		this.iconText = iconText;
	}

}