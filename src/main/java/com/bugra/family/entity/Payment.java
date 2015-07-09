package com.bugra.family.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private BigDecimal amount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private Short month;

	private String name;

	private Short year;

	@ManyToOne
	@JoinColumn(name="parent_id")
	@JsonIgnore
	private Payment parentPayment;

	@OneToMany(mappedBy="parentPayment", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	@JsonIgnore
	private List<Payment> childPayments;

	@OneToMany(mappedBy="payment", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<PaymentTag> tags;
		
	@Column(name="parent_id", insertable=false, updatable=false)
	private Integer parentId;
	
	public Payment() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}