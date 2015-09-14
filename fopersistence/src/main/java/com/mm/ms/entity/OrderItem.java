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
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Transient
	public final String entityname = "OrderItem";

	// START

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Long uid;
	// END

	@Column
	private Long item_id;

	@Column
	private int qty;

	@Column
	private long oid;

	public OrderItem() {

	}

	public OrderItem(long id) {
		this.id = id;
	}

	@Transient
	public String fetchLogDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(entityname + " -> id = ").append(id)
				.append(", uid = ").append(uid).append(", item_id=")
				.append(item_id).append(", qty=").append(qty).append(", oid=")
				.append(oid).append("]");
		return sb.toString();
	}

	@Transient
	public OrderItem mergeUpdates(OrderItem tobemerged) {
		this.id = (null != tobemerged.getId()
				&& !("".equals(tobemerged.getId()))
				&& !(" ".equals(tobemerged.getId())) ? tobemerged.getId()
				: this.getId());
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

}
