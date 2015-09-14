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
public class Foodorder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@Transient
	public final String entityname = "Order";

	//START
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderdate;
	
	@Column
	private String status;
	//END
	
	public Foodorder(){
		
	}
	
	public Foodorder(String name) {		
		this.name = name;	
		orderdate = new Date();
		status = "Open";
	}

	
	@Transient
	public String fetchLogDetails(){
		StringBuilder sb = new StringBuilder();
		sb.append("[")
		.append(entityname + " -> id = ").append(id)		
		.append(", name = ").append(name)		
		.append(", orderdate = ").append(orderdate)	
		.append(", status = ").append(status)	
		.append("]");
		return sb.toString();
	}
	
	@Transient
	public Foodorder mergeUpdates(Foodorder tobemerged) {		
		this.name = (null != tobemerged.getName()
				&& !("".equals(tobemerged.getName()))
				&& !(" ".equals(tobemerged.getName())) ? tobemerged.getName() : this.getName());
		
		this.orderdate = (null != tobemerged.getOrderdate() ? tobemerged.getOrderdate() : this.getOrderdate());
				
		this.status = (null != tobemerged.getStatus()
				&& !("".equals(tobemerged.getStatus()))
				&& !(" ".equals(tobemerged.getStatus())) ?  tobemerged.getStatus() : this.getStatus());
		
		return this;		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}
