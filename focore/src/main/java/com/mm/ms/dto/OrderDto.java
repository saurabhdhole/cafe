package com.mm.ms.dto;

import java.util.Date;

public class OrderDto {
	
	private String ordername;
	private Date orderdate;
	private Long orderidL;
	private Long orderidB;
	private Long orderidD;
	private Long ordercnt;
	private String lunchrec;
	private String breakfastrec;
	private String dinnerrec;
	
	
	public String getLunchrec() {
		return lunchrec;
	}

	public void setLunchrec(String lunchrec) {
		this.lunchrec = lunchrec;
	}

	public String getBreakfastrec() {
		return breakfastrec;
	}

	public void setBreakfastrec(String breakfastrec) {
		this.breakfastrec = breakfastrec;
	}

	public String getDinnerrec() {
		return dinnerrec;
	}

	public void setDinnerrec(String dinnerrec) {
		this.dinnerrec = dinnerrec;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public Long getOrdercnt() {
		return ordercnt;
	}

	public void setOrdercnt(Long ordercnt) {
		this.ordercnt = ordercnt;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Long getOrderidL() {
		return orderidL;
	}

	public void setOrderidL(Long orderidL) {
		this.orderidL = orderidL;
	}

	public Long getOrderidB() {
		return orderidB;
	}

	public void setOrderidB(Long orderidB) {
		this.orderidB = orderidB;
	}

	public Long getOrderidD() {
		return orderidD;
	}

	public void setOrderidD(Long orderidD) {
		this.orderidD = orderidD;
	}

	public String getOrdername() {
		return ordername;
	}

	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}
	

	
}
