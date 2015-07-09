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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;

	@ManyToOne
	@JsonIgnore
	private Payment payment;

	@ManyToOne
	@JsonIgnore
	private Tag tag;

	@Column(name = "tag_id", insertable=false, updatable=false)
	private Integer tagId;
	
	public PaymentTag() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

}