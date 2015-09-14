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
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@Transient
	public final String entityname = "User";

	//START
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	//END
	
	public User(){
		
	}
	
	public User(String name) {		
		this.name = name;		
	}

	@Transient
	public String fetchLogDetails(){
		StringBuilder sb = new StringBuilder();
		sb.append("[")
		.append(entityname + " -> id = ").append(id)		
		.append(", name = ").append(name)		
		.append("]");
		return sb.toString();
	}
	
	@Transient
	public User mergeUpdates(User tobemerged) {		
		this.name = (null != tobemerged.getName()
				&& !("".equals(tobemerged.getName()))
				&& !(" ".equals(tobemerged.getName())) ? tobemerged.getName() : this.getName());		
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
}
