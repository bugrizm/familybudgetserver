package com.bugra.family.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@Table(name="payment")
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private BigDecimal amount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private Short month;

	private String name;

	private Short year;

	@ManyToOne
	@JoinColumn(name="parent_id")
	private Payment parentPayment;

	@OneToMany(mappedBy="parentPayment", fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Payment> childPayments;

	@OneToMany(mappedBy="payment", fetch=FetchType.EAGER)
	private List<PaymentTag> tags;

	public Payment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Short getMonth() {
		return this.month;
	}

	public void setMonth(Short month) {
		this.month = month;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getYear() {
		return this.year;
	}

	public void setYear(Short year) {
		this.year = year;
	}

	public Payment getParentPayment() {
		return this.parentPayment;
	}

	public void setParentPayment(Payment parentPayment) {
		this.parentPayment = parentPayment;
	}

	public List<Payment> getChildPayments() {
		return this.childPayments;
	}

	public void setChildPayments(List<Payment> childPayments) {
		this.childPayments = childPayments;
	}

	public Payment addChildPayment(Payment childPayment) {
		getChildPayments().add(childPayment);
		childPayment.setParentPayment(this);

		return childPayment;
	}

	public Payment removeChildPayment(Payment childPayment) {
		getChildPayments().remove(childPayment);
		childPayment.setParentPayment(null);

		return childPayment;
	}

	public List<PaymentTag> getTags() {
		return this.tags;
	}

	public void setTags(List<PaymentTag> tags) {
		this.tags = tags;
	}

	public PaymentTag addTag(PaymentTag tag) {
		getTags().add(tag);
		tag.setPayment(this);

		return tag;
	}

	public PaymentTag removeTag(PaymentTag tag) {
		getTags().remove(tag);
		tag.setPayment(null);

		return tag;
	}

}