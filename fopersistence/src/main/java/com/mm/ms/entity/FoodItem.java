package com.mm.ms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FoodItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@Transient
	public final String entityname = "FoodItem";

	//START
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private Double price;
	//END
	
	public FoodItem(){
		
	}
	
	public FoodItem(String name,Double price) {		
		this.name = name;		
		this.price=price;
	}

	@Transient
	public String fetchLogDetails(){
		StringBuilder sb = new StringBuilder();
		sb.append("[")
		.append(entityname + " -> id = ").append(id)		
		.append(", name = ").append(name)
		.append(", price = ").append(price)
		.append("]");
		return sb.toString();
	}
	
	@Transient
	public FoodItem mergeUpdates(FoodItem tobemerged) {		
		this.name = (null != tobemerged.getName()
				&& !("".equals(tobemerged.getName()))
				&& !(" ".equals(tobemerged.getName())) ? tobemerged.getName() : this.getName());		
		
		
		this.price = (0.00 != tobemerged.getPrice() ? tobemerged.getPrice() 
				: this.getPrice());	
		
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
	
	}
