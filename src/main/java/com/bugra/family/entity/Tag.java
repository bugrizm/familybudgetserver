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
	private int id;

	@Column(name="limit_amount")
	private BigDecimal limitAmount;

	private String name;

	//bi-directional many-to-one association to PaymentTag
	@OneToMany(mappedBy="tag")
	@JsonIgnore
	private List<PaymentTag> payments;

	public Tag() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getLimitAmount() {
		return this.limitAmount;
	}

	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
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

}