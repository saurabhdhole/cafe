package com.mm.ms.dto;

public class ReconciliationDto {
	
	private Long orderid;
	private Long userid;
	private String username;
	private Double amtpaid;
	private Double cost;
	private String status;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getOrderid() {
		return orderid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Double getAmtpaid() {
		return amtpaid;
	}
	public void setAmtpaid(Double amtpaid) {
		this.amtpaid = amtpaid;
	}
	
	
	
	
}
