package com.mm.ms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reconciliation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@Transient
	public final String entityname = "Reconciliation";

	//START
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private Long userid;
	
	@Column
	private Long orderid;
	
	@Column
	private Double ordercost;
	
	@Column
	private Double amountpaid;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column
	private String status;
	
	@Transient
	public String fetchLogDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(entityname + " -> id = ").append(id)
				.append(", user_id = ").append(userid).append(", order_id = ").append(orderid)
				.append(", order_cost = ").append(ordercost).append(", amount_paid = ").append(amountpaid)
				.append(", date = ").append(date).append(", order_status = ").append(status)
				.append("]");
		return sb.toString();
	}
	
	@Transient
	public Reconciliation mergeUpdates(Reconciliation tobemerged) {
		this.userid = (null != tobemerged.getUserid()
				 ? tobemerged.getUserid(): this.getUserid());

		this.orderid = (null != tobemerged.getOrderid()
				 ? tobemerged.getOrderid() : this.getOrderid());

		this.ordercost = (null != tobemerged.getOrdercost()
				 ? tobemerged.getOrdercost() : this.getOrdercost());
		
		this.amountpaid = (null != tobemerged.getAmountpaid()
				 ? tobemerged.getAmountpaid(): this.getAmountpaid());

		this.date = (null != tobemerged.getDate()
				 ? tobemerged.getDate() : this.getDate());

		this.status = (null != tobemerged.getStatus()
				&& !("".equals(tobemerged.getStatus()))
				&& !(" ".equals(tobemerged.getStatus())) ? tobemerged.getStatus() : this.getStatus());

		return this;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Double getOrdercost() {
		return ordercost;
	}

	public void setOrdercost(Double ordercost) {
		this.ordercost = ordercost;
	}

	public Double getAmountpaid() {
		return amountpaid;
	}

	public void setAmountpaid(Double amountpaid) {
		this.amountpaid = amountpaid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}
