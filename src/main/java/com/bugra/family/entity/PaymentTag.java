package com.bugra.family.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the payment_tag database table.
 * 
 */
@Entity
@Table(name="payment_tag")
@NamedQuery(name="PaymentTag.findAll", query="SELECT p FROM PaymentTag p")
public class PaymentTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@ManyToOne
	@JsonIgnore
	private Payment payment;

	@ManyToOne
	private Tag tag;

	public PaymentTag() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}